package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.AuthenticationDTO;
import com.example.demo.domain.user.LoginResponseDTO;
import com.example.demo.domain.user.RegisterDTO;
import com.example.demo.domain.user.Users;
import com.example.demo.infra.security.TokenService;
import com.example.demo.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		  var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		  var auth = this.authenticationManager.authenticate(usernamePassword);
		  
		  var token = tokenService.generateToken((Users)auth.getPrincipal());
		  
		  return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		Users newUser = new Users(data.login(), encryptedPassword, data.role());
		
		
		this.repository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
	
}
