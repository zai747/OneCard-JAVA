package com.mdot.app.controllers;

import javax.validation.Valid;

import com.mdot.app.payloads.requests.OneMediaRequest;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.OneMediaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/onemedia")
public class OneMediaController {
    @Autowired
    private OneMediaService onemediaService;

    @PostMapping("/save/{id}")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser,
    @PathVariable("id") long id,
            @Valid @RequestBody OneMediaRequest onemediaRequest) {
        return ResponseEntity.ok(onemediaService.save(onemediaRequest,id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody OneMediaRequest onemediaRequest, @PathVariable("id") long id) {
        return onemediaService.update(id, onemediaRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.onemediaService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.onemediaService.listById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> listByUserId(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.onemediaService.listByUserId(id));
    }

}