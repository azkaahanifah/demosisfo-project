package com.coder.demosisfo.service;

import com.coder.demosisfo.exception.PasswordFormatException;
import com.coder.demosisfo.payload.JwtResponse;
import com.coder.demosisfo.payload.request.LoginRequest;
import com.coder.demosisfo.payload.request.RegisterRequest;

public interface AuthService {
	public String registerUser(RegisterRequest registerRequest) throws PasswordFormatException;
	public JwtResponse jwtResponse(LoginRequest loginRequest);
}
