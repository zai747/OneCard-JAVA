package com.mdot.app.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String username;
    private String phone;
    private String email;
    private String usermedia;
    private String profileimage;
    private String projects;
    private String jobtitle;
    private String description;
    private String firstname;
    private String lastname;


}
