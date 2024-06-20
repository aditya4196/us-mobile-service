package com.demo.usmobile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.CreateUserDTO;
import com.demo.usmobile.exception.UserAlreadyExistsException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }
	

	/**
	 * 
	 * @param userdto
	 * @return
	 */
	public User createUser(final CreateUserDTO userdto) {
			if(userRepository.findByEmail(userdto.getEmail()).isPresent()) {
				log.error("User with email {} already exists",userdto.getEmail());
				throw new UserAlreadyExistsException("User with email " + userdto.getEmail() + " already exists");
			}
			
			final BCryptPasswordEncoder bcryptencoder = new BCryptPasswordEncoder();
			final String encryptedPassword = bcryptencoder.encode(userdto.getPassword());
			
			User user = User.builder()
					.firstName(userdto.getFirstName())
					.lastName(userdto.getLastName())
					.email(userdto.getEmail())
					.password(encryptedPassword)
					.build();
			log.info(user.toString());
			User createdUser = userRepository.save(user);
			return createdUser;
		
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Optional<User> findUserById(final String userId){
		final Optional<User> user = userRepository.findById(userId);
	
		if(!user.isPresent()) {
			log.error("User with userId {} not found", userId);
			throw new UserNotFoundException("User with userId " + userId + " not found");
		}
		return user;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<User> getAllUsers(){

		final List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			log.info("no users found");
			throw new UserNotFoundException("No users present");
		}
		return users;	
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param patch
	 * @return
	 * @throws JsonProcessingException
	 * @throws JsonPatchException
	 */
	public User updateUser(String userId, JsonPatch patch) throws JsonProcessingException, JsonPatchException{
		final Optional<User> fetchedUser = findUserById(userId);
		var jsonPatchList = objectMapper.convertValue(patch, JsonNode.class);
		jsonPatchList.forEach((currPatch) -> {
			if(currPatch.get("path").equals("password")) {
				log.error("Password cannot be updated");			}
		});
		
		if(fetchedUser.isPresent()) {
			User patchedUser = fetchedUser.get();
			return userRepository.save(applyPatchToUser(patch, patchedUser));
		}
		else {
			throw new UserNotFoundException("User not found for user Id" + userId);
		}
	}
	
	public User applyPatchToUser(
			  JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
				
			    return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(targetUser, JsonNode.class)), User.class);
			}
	
	

}
