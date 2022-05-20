package com.mdot.app.controllers;

import javax.validation.Valid;

import com.mdot.app.payloads.requests.FriendRequest;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.FriendService;

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
@RequestMapping(path = "/api/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @PostMapping("/save/{friend}")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser, @PathVariable("friend") Long friend)
         {
       
        return ResponseEntity.ok(friendService.save(currentuser.getId(), friend));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody FriendRequest friendRequest, @PathVariable("id") long id) {
        return friendService.update(id, friendRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.friendService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.friendService.listById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@CurrentUser UserPrincipal currentuser) {
        return ResponseEntity.ok(this.friendService.list());
    }
}