package com.demo.usmobile.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
	
	private String firstName;
	private String lastName;
    private String email;
	
}
