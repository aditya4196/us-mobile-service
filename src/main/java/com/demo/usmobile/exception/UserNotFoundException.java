package com.demo.usmobile.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
