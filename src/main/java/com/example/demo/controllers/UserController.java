package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.UserUpdateDTO;
import com.example.demo.domain.user.Users;
import com.example.demo.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping(value = "/{id}")
	public Optional<Users> findUserById(@PathVariable("id") Integer id) {
		return service.findUserById(id);
	}
	
	@PostMapping(value = "/update_user")
	public Optional<Users> updateUser(@RequestBody UserUpdateDTO user) {
		return service.updateUser(user);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id) {
		service.deleteUserById(id);
		return ResponseEntity.noContent().build();
	} 

}
