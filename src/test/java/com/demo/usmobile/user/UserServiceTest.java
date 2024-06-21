package com.demo.usmobile.user;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.CreateUserDTO;
import com.demo.usmobile.exception.UserAlreadyExistsException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.UserRepository;
import com.demo.usmobile.service.UserService;
import com.github.fge.jsonpatch.JsonPatchException;



@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
	
    
    @DisplayName("Find User by id")
    @Test
    public void UserService_findUserById_Success() {
    	
    	User user = User.builder()
    			.firstName("Aditya")
    			.lastName("Jadhav")
    			.email("adityajadhav@gmail.com")
    			.password("rdfgdfg")
    			.build();

    	when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
    	
    	Optional<User> fetchedUser = userService.findUserById("1");
    	assertTrue(fetchedUser.isPresent());
    	
    }
    
    
	@DisplayName("Find a userbyId throws UserNotFoundException")
	@Test
	public void userRepository_findUserByIdThrowsUserNotFoundException(){
		
		//Arrange
		when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserById("1"));
	}
	
     
	@DisplayName("Create a user")
	@Test
	public void userRepository_createUser_Success() throws IOException {
		
		//Arrange
		CreateUserDTO inputUserDTO = CreateUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("jdoe@gmail.com")
				.password("thisissecret")
				.build();

		final BCryptPasswordEncoder bcryptencoder = new BCryptPasswordEncoder();
		User inputUser = User.builder()
				.firstName(inputUserDTO.getFirstName())
				.lastName(inputUserDTO.getLastName())
				.email(inputUserDTO.getEmail())
				.password(bcryptencoder.encode(inputUserDTO.getPassword()))
				.build();
		

		when(userRepository.save(any())).thenReturn(inputUser);

		//Act	
		User savedUser = userService.createUser(inputUserDTO);	
		
		//Assert
		assertThat(savedUser.getFirstName()).isEqualTo(inputUser.getFirstName());
		assertThat(savedUser.getLastName()).isEqualTo(inputUser.getLastName());
		assertThat(savedUser.getEmail()).isEqualTo(inputUser.getEmail());	
	}
	
	
	@DisplayName("Create a user throws UserAlreadyExistsException")
	@Test
	public void userRepository_createUserTestThrowsUserAlreadyExistsException() throws IOException {
		
		//Arrange
		CreateUserDTO inputUserDTO = CreateUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("jdoe@gmail.com")
				.password("thisissecret")
				.build();

		final BCryptPasswordEncoder bcryptencoder = new BCryptPasswordEncoder();
		User inputUser = User.builder()
				.firstName(inputUserDTO.getFirstName())
				.lastName(inputUserDTO.getLastName())
				.email(inputUserDTO.getEmail())
				.password(bcryptencoder.encode(inputUserDTO.getPassword()))
				.build();
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(inputUser));

		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(inputUserDTO));
	}
	
	@DisplayName("Update an existing user throws UserNotFoundException")
	@Test
	public void userRepository_updateUserThrowsUserNotFoundException() throws IOException, JsonPatchException {
		
		//Arrange
		when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser("1", null));
	}
	

}

