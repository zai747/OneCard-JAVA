package com.mdot.app.payloads.requests;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

	
	private String Username;
	private String Password;
	private String Name;
	private String email = "";
	private String phone = "";
	
}