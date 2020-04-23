package com.coder.demosisfo.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.constant.LoggerMsg;
import com.coder.demosisfo.exception.PasswordFormatException;
import com.coder.demosisfo.model.ERole;
import com.coder.demosisfo.model.Role;
import com.coder.demosisfo.model.User;
import com.coder.demosisfo.payload.JwtResponse;
import com.coder.demosisfo.payload.request.LoginRequest;
import com.coder.demosisfo.payload.request.RegisterRequest;
import com.coder.demosisfo.repository.RoleRepository;
import com.coder.demosisfo.repository.UserRepository;
import com.coder.demosisfo.security.jwt.JwtUtils;
import com.coder.demosisfo.service.AuthService;
import com.coder.demosisfo.util.RegexUtil;

@Service("authService")
public class AuthServiceImpl implements AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	StudentServiceImpl studentServiceImpl;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RegexUtil regexUtil;

	@Override
	public String registerUser(RegisterRequest registerRequest) throws PasswordFormatException {
		boolean password = true;
		if (password == regexUtil.matchesPassword(registerRequest.getPassword())) {
			LOGGER.info("Create new user account");
			User user = new User(registerRequest.getFullname(), registerRequest.getUsername(),
					passwordEncoder.encode(registerRequest.getPassword()));

			Set<String> strRoles = registerRequest.getRole();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
				roles.add(adminRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "dosen":
						Role dosenRole = roleRepository.findByName(ERole.ROLE_DOSEN)
								.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
						roles.add(dosenRole);
						break;

					case "staff":
						Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
								.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
						roles.add(staffRole);
						break;

					case "mahasiswa":
						Role mahasiswaRole = roleRepository.findByName(ERole.ROLE_MAHASISWA)
								.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
						roles.add(mahasiswaRole);
						break;

					default:
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
						roles.add(adminRole);
						break;
					}
				});
			}
			user.setRoles(roles);
			userRepository.save(user);
		} else {
			LOGGER.error(
					"Password must contain at least 8 character, 1 number, 1 uppercase and lowercase letter, 1 special character and not contain any space");
			throw new PasswordFormatException("Password does not unique");
		}

		return "User registered successfully!";
	}

	@Override
	public JwtResponse jwtResponse(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		return new JwtResponse(jwt);
	}

}
