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
 
    private String Profileimage;
    private String Jobtitle;

    private String Usermedia;
    private String Projects;

    private String description;
 

}
