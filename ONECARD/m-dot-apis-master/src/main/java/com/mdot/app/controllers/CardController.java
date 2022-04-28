package com.mdot.app.controllers;

import javax.validation.Valid;

import com.mdot.app.payloads.requests.CardRequest;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.CardService;

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
@RequestMapping(path = "/api/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/save/{id}/{cardid}")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser, @PathVariable("cardid") String cardid)
         {
       
        return ResponseEntity.ok(cardService.save(currentuser.getId(), cardid));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.cardService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.cardService.listById(id));
    }

}