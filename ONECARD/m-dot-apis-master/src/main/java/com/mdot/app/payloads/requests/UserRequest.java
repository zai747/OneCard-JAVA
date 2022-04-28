package com.mdot.app.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String phone;
    private String email;
    private String password;
    private String status;

   private String firstname;
   private String lastname;
 
    private String jobtitle;

    private String usermedia;
    private String projects;

    private String description;

    private String profileimage;
 

}
