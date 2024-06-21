package com.demo.usmobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.CreateUserDTO;
import com.demo.usmobile.exception.ModificationProhibitedException;
import com.demo.usmobile.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.validation.Valid;


/**
 * User Controller Layer
 */
@RestController
@Validated
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * endpoint to get all Users
	 * @param page
	 * @param size
	 * @return Paginated User Object
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers(
			@RequestParam("page") int page,
			@RequestParam("size") int size){
		Pageable pageable = PageRequest.of(page, size);
		Page<User> userList = userService.getAllUsers(pageable);
		return new ResponseEntity<>(userList, HttpStatus.OK);	
	}
	
	/**
	 * endpoint to create a new user
	 * @param userdto
	 * @return User
	 */
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserDTO userdto) {
			User newUser = userService.createUser(userdto);  
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	/**
	 * endpoint to update an existing user
	 * @param userId
	 * @param patch - contains fields to be replaced
	 * @return Updated User Object
	 * @throws JsonProcessingException
	 * @throws JsonPatchException
	 * @throws ModificationProhibitedException 
	 */
	@PatchMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestParam("userId") String userId,
			@RequestBody JsonPatch patch) throws JsonProcessingException, JsonPatchException, ModificationProhibitedException {
			User updatedUser = userService.updateUser(userId, patch);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	

}
