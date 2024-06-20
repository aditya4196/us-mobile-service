package com.demo.usmobile.exception;

public class ModificationProhibitedException extends Exception{
	
	public ModificationProhibitedException() {
		super();
	}	
	
	public ModificationProhibitedException(String message) {
		super(message);
	}

}
