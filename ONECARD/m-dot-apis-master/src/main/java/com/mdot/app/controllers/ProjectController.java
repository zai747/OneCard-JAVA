package com.mdot.app.controllers;

import javax.validation.Valid;

import com.mdot.app.payloads.requests.ProjectRequest;

import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.ProjectService;

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
@RequestMapping(path = "/api/project")
public class ProjectController {
    @Autowired 
    private ProjectService projectService;

    @PostMapping("/save/{id}/{title}/{description}")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser, @PathVariable("title") String title,
            @PathVariable("description") String description) {
        if (description.equals("-x"))
            description= "";
        return ResponseEntity.ok(projectService.save(currentuser.getId(), title, description));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody ProjectRequest projectRequest, @PathVariable("id") long id) {
        return projectService.update(id, projectRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.projectService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.projectService.listById(id));
    }

}