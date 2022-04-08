package com.mdot.app.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import com.mdot.app.exceptions.BadRequestException;
import com.mdot.app.models.JwtRefreshToken;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.LoginRequest;
import com.mdot.app.payloads.requests.RefreshTokenRequest;
import com.mdot.app.payloads.responses.JwtAuthenticationResponse;
import com.mdot.app.repositories.JwtRefreshTokenRepository;
import com.mdot.app.repositories.UserRepository;
import com.mdot.app.securities.JwtTokenProvider;
import com.mdot.app.securities.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> register(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		String jwt = tokenProvider.generateToken(userPrincipal);
		String refreshToken = tokenProvider.generateRefreshToken();
		saveRefreshToken(userPrincipal, refreshToken);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshToken, userPrincipal));
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