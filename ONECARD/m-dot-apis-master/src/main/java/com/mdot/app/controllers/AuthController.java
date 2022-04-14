package com.mdot.app.controllers;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import javax.validation.Valid;

import com.mdot.app.exceptions.AppException;
import com.mdot.app.exceptions.BadRequestException;
import com.mdot.app.models.JwtRefreshToken;
import com.mdot.app.models.Role;
import com.mdot.app.models.RoleName;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.LoginRequest;
import com.mdot.app.payloads.requests.RefreshTokenRequest;
import com.mdot.app.payloads.requests.SignUpRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.payloads.responses.JwtAuthenticationResponse;
import com.mdot.app.repositories.JwtRefreshTokenRepository;
import com.mdot.app.repositories.RoleRepository;
import com.mdot.app.repositories.UserRepository;
import com.mdot.app.securities.CurrentUser;
import com.mdot.app.securities.JwtTokenProvider;
import com.mdot.app.securities.UserPrincipal;
import com.mdot.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtRefreshTokenRepository jwtRefreshTokenRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		String jwt = tokenProvider.generateToken(userPrincipal);
		String refreshToken = tokenProvider.generateRefreshToken();
		saveRefreshToken(userPrincipal, refreshToken);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshToken, userPrincipal));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@CurrentUser UserPrincipal currentuser,
			@Valid @RequestBody SignUpRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername()))
			return new ResponseEntity<>(new ApiResponse(false, "Username already in use"), HttpStatus.BAD_REQUEST);

		if (userRepository.existsByPhone(signUpRequest.getPhone()))
			return new ResponseEntity<>(new ApiResponse(false, "Phone number already in use"), HttpStatus.BAD_REQUEST);

		User user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(signUpRequest.getPassword());
	    //user.setName(signUpRequest.getName());
		//user.setEmail(signUpRequest.getEmail());
		//user.setPhone(signUpRequest.getPhone());
		//user.setGender(signUpRequest.getGender());
		
	/*	LocalDate dob = LocalDate.now();
		try {
			dob = LocalDate.parse(signUpRequest.getDob());
		} catch(Exception e) {
			dob = LocalDate.now();
		}*/
		
		/*user.setDob(dob);
		user.setAddress(signUpRequest.getAddress());
		user.setCity(signUpRequest.getCity());
		user.setState(signUpRequest.getState());
		user.setCountry(signUpRequest.getCountry());
		user.setPin(signUpRequest.getPin());*/
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		RoleName role = RoleName.ROLE_USER;
		Role userRole = roleRepository.findByName(role).orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		User result = this.userService.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{id}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully", user));
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return jwtRefreshTokenRepository.findById(refreshTokenRequest.getRefreshToken()).map(jwtRefreshToken -> {
			User user = jwtRefreshToken.getUser();
			UserPrincipal userPrincipal = UserPrincipal.create(user);
			String accessToken = tokenProvider.generateToken(UserPrincipal.create(user));
			return ResponseEntity
					.ok(new JwtAuthenticationResponse(accessToken, jwtRefreshToken.getToken(), userPrincipal));
		}).orElseThrow(() -> new BadRequestException("Invalid Refresh Token"));
	}

	private void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken) {
		JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken);
		// jwtRefreshToken.setUser(userRepository.getOne(userPrincipal.getId()));
		jwtRefreshToken.setUser(userRepository.findById(userPrincipal.getId()).get());
		Instant expirationDateTime = Instant.now().plus(360, ChronoUnit.DAYS);
		jwtRefreshToken.setExpirationDateTime(expirationDateTime);
		jwtRefreshTokenRepository.save(jwtRefreshToken);
	}

}