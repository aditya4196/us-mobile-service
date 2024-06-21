package com.demo.usmobile.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.documents.User;
import com.demo.usmobile.repository.UserRepository;

@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@DisplayName("Save User in DB")
	@Test
	public void UserRepository_SaveUser_ReturnsUser() {
    	
    	User fetchedUser = userRepository.save(User.builder()
    			.firstName("Aditya")
    			.lastName("Jadhav")
    			.email("adityajadhav@gmail.com")
    			.password("rdfgdfg")
    			.build());
    	
    	assertThat(fetchedUser).isNotNull();
	}

	@DisplayName("Update User properties in DB")
	@Test
	public void UserRepository_UpdateUser_ReturnsUser() {
    	
		User insertedUser = userRepository.save(User.builder()
    			.firstName("Aditya")
    			.lastName("Jadhav")
    			.email("adityajadhav@gmail.com")
    			.password("rdfgdfg")
    			.build());
		
		insertedUser.setFirstName("Swapnil");
		insertedUser.setLastName("Shinde");
		
		User updatedUser = userRepository.save(insertedUser);

    	assertThat(updatedUser.getFirstName()).isEqualTo("Swapnil");
    	assertThat(updatedUser.getLastName()).isEqualTo("Shinde");
	}
	
	
	
	@DisplayName("Find User By Id")
	@Test
	public void UserRepository_finUserById_ReturnsUser() {
    	
		User savedUser = userRepository.save(User.builder()
    			.firstName("Aditya")
    			.lastName("Jadhav")
    			.email("adityajadhav@gmail.com")
    			.password("rdfgdfg")
    			.build());
		
    	assertThat(userRepository.findById(savedUser.getId())).isNotNull();
	}

}
