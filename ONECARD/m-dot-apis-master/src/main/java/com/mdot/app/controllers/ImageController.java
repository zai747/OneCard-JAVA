package com.mdot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")

public class ImageController {

    @Value("${app.uploadPath}")
    private String uploadPath;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{filename}")
    public ResponseEntity<?> showImage(@PathVariable("filename") String fileName) throws IOException {
        try {
            File serverFile = new File(this.uploadPath + fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Files.readAllBytes(serverFile.toPath()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "File not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{filename}")
    public ResponseEntity<?> save(@CurrentUser UserPrincipal currentuser,
            @RequestParam(value = "image", required = false) MultipartFile uploadfile,
            @PathVariable("filename") String fileName) {

        String imageStatusMsg = "";

        try {
            if (null != uploadfile.getContentType()) {
                ApiResponse imageStatus = this.imageService.saveImage(uploadfile, fileName);
                if (imageStatus.getSuccess()) {
                    imageStatusMsg = "Image process successfully completed";
                } else {
                    imageStatusMsg = "Image process failed";
                    System.out.println(imageStatusMsg);
                    return new ResponseEntity<>(new ApiResponse(false, fileName), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            imageStatusMsg = "Image process failed";
            System.out.println(imageStatusMsg);
            System.out.println(e.toString());
            return new ResponseEntity<>(new ApiResponse(false, fileName), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("File : "+imageStatusMsg);
    }

}
