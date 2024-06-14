package com.demo.usmobile.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usmobile.model.UserDTO;
import com.demo.usmobile.repository.UsersRepository;

@RestController
public class UserController {
	
	@Autowired
	private UsersRepository userRepo;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity getAllUsers(){
		
		List<UserDTO> userList = userRepo.findAll();
		System.out.println(userList.size());
		if(userList.size() > 0) {
			return new ResponseEntity<List<UserDTO>>(userList, HttpStatus.OK);
		}
		else return new ResponseEntity<>("No user found", HttpStatus.OK);
		
	}
	
	@PostMapping("/addUser")
	public ResponseEntity addUser(@RequestBody UserDTO user) {
		try {
			user.setCreatedAt(new Date(System.currentTimeMillis()));
			user.setUpdatedAt(user.getCreatedAt());
			userRepo.save(user);
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		}
		catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	 
	
	
	

}
