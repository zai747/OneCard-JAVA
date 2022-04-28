package com.mdot.app.payloads.requests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
	
	private String fcmId;

	
	@NotBlank
	private String phone;

}
