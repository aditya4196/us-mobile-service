package com.demo.usmobile.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.CreateUserDTO;
import com.demo.usmobile.exception.ModificationProhibitedException;
import com.demo.usmobile.exception.UserAlreadyExistsException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import lombok.extern.slf4j.Slf4j;

/**
 * User Service Layer
 */
@Service
@Slf4j
public class UserService {
	
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private boolean isPasswordProvided;

    /**
     * 
     * @param userRepository
     * @param objectMapper
     */
    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }
	

	/**
	 * creates the user
	 * @param userdto
	 * @return User
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
	 * finds user by Id
	 * @param userId
	 * @return Optional<User>
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
	 * gets all users
	 * @return Paginated Users
	 */
	public Page<User> getAllUsers(Pageable pageable){

		final Page<User> users = userRepository.findAll(pageable);
		if(users.isEmpty()) {
			log.info("no users found");
			throw new UserNotFoundException("No users present");
		}
		return users;	
	}
	
	
	/**
	 * updates an existing user properties except password
	 * @param userId
	 * @param patch
	 * @return User
	 * @throws JsonProcessingException
	 * @throws JsonPatchException
	 * @throws ModificationProhibitedException 
	 */
	public User updateUser(String userId, JsonPatch patch) throws JsonProcessingException, JsonPatchException, ModificationProhibitedException{
		final Optional<User> fetchedUser = findUserById(userId);
		JsonNode jsonPatchList = objectMapper.convertValue(patch, JsonNode.class);
		
		
		if (jsonPatchList.isArray()) {
            for (JsonNode patchNode : jsonPatchList) {
                // Fetch the "path" field from each object node in the array
                JsonNode pathNode = patchNode.get("path");
                if (pathNode != null && pathNode.isTextual()) {
                    String path = pathNode.asText();
                    if(path.equals("/password")) {
                    	isPasswordProvided = true;
                    	break;
                    }
                }
            }
        }
		
		if(isPasswordProvided) {
			isPasswordProvided = false;
			throw new ModificationProhibitedException("Password cannot be modified, Please resend the request with either FirstName, LastName or Email");
		}
		
		if(fetchedUser.isPresent()) {
			User patchedUser = fetchedUser.get();
			return userRepository.save(applyPatchToUser(patch, patchedUser));
		}
		else {
			throw new UserNotFoundException("User not found for user Id" + userId);
		}
	}
	
	/**
	 * applies the patch to the existing user to modify properties
	 * @param patch
	 * @param targetUser
	 * @return User
	 * @throws JsonPatchException
	 * @throws JsonProcessingException
	 */
	public User applyPatchToUser(
			  JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
				
			    return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(targetUser, JsonNode.class)), User.class);
			}
	
	

}
