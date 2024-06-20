package com.demo.usmobile.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataRequestDTO {	
	
	@NotBlank(message = "User Id is mandatory")
	private String userId;
	
	@NotBlank(message = "Mdn is mandatory")
	private String mdn;
}
