package com.coder.demosisfo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.demosisfo.exception.PasswordFormatException;
import com.coder.demosisfo.payload.JwtResponse;
import com.coder.demosisfo.payload.MessageResponse;
import com.coder.demosisfo.payload.request.LoginRequest;
import com.coder.demosisfo.payload.request.RegisterRequest;
import com.coder.demosisfo.repository.UserRepository;
import com.coder.demosisfo.service.impl.AuthServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthServiceImpl authServiceImpl;
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws PasswordFormatException{
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		String response = authServiceImpl.registerUser(registerRequest);		
		return ResponseEntity.ok(new MessageResponse(response));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = authServiceImpl.jwtResponse(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}

}
