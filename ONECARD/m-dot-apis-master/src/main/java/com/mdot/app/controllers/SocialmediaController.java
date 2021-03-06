package com.mdot.app.controllers;

import javax.validation.Valid;


import com.mdot.app.payloads.requests.SocialmediaRequest;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.SocialmediaService;

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
@RequestMapping(path = "/api/socialmedia")
public class SocialmediaController {
    @Autowired
    private SocialmediaService socialmediaService;

    @PostMapping("/save/{id}")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser,
    @PathVariable("id") long id,
            @Valid @RequestBody SocialmediaRequest socialmediaRequest) {
        return ResponseEntity.ok(socialmediaService.save(socialmediaRequest,id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody SocialmediaRequest socialmediaRequest, @PathVariable("id") long id) {
        return socialmediaService.update(id, socialmediaRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.socialmediaService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.socialmediaService.listById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> listByUserId(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.socialmediaService.listByUserId(id));
    }
}