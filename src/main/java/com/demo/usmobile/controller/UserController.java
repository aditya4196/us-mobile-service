package com.demo.usmobile.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.CreateUserDTO;
import com.demo.usmobile.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.validation.Valid;


@RestController
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers(){	
		List<User> userList = userService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);	
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserDTO userdto) {
			User newUser = userService.createUser(userdto);  
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	@PatchMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestParam("userId") String userId,
			@RequestBody JsonPatch patch) throws JsonProcessingException, JsonPatchException {
			User updatedUser = userService.updateUser(userId, patch);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	
	/*
	 * 
	 * improve the code using sonarLint
	 * 
	 * 
	 * 
	 */
	
	
	

}
