package com.demo.usmobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UsmobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsmobileApplication.class, args);
	}

}

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
