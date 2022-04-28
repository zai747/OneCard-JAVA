package com.mdot.app.payloads.requests;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

	
	private String username;
	private String password;
	private String name;
	private String email = "";
	private String phone = "";
	private String description;
	private String jobtitle;
	private String usermedia;
	private String projects;
	private String profileimage;
	private String firstname;
	private String lastname;




}