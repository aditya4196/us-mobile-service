package com.demo.usmobile.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDTO {
	
	@NotNull(message = "First Name is Mandatory")
	private String firstName;
	
	@NotNull(message = "Last Name is Mandatory")
	private String lastName;
	
	@NotNull(message = "Email is Mandatory")
    @Email(message = "Email should be valid")
    private String email;
	
	@NotNull(message = "Password is Mandatory")
	private String password;

}
