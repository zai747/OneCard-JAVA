package com.mdot.app.services;

import com.mdot.app.payloads.responses.ApiResponse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Value("${app.uploadPath}")
    private String uploadPath;

    @Autowired
    AuthenticationManager authenticationManager;

    @Transactional
    public ApiResponse saveImage(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty())
                return new ApiResponse(false, "File is empty");

            byte[] bytes = file.getBytes();
            Path path = Paths.get(this.uploadPath + fileName);
            Files.write(path, bytes);
            return new ApiResponse(true, "Image saved successfully");
        } catch (Exception e) {
            System.out.println("Error in saving image : " + e);
        }
        return new ApiResponse(false, "Image not saved");
    }

    @Transactional
    public ApiResponse removeUploadedFile(String fileName) {
        try {
            File file = new File(this.uploadPath + fileName);
            if (file.delete())
                return new ApiResponse(true, "Image deleted successfully");
        } catch (Exception e) {
            System.out.println("Error in deleting image : " + e);
        }
        return new ApiResponse(false, "Image not deleted");
    }

}
