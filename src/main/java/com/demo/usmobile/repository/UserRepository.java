package com.demo.usmobile.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.documents.User;

/**
 * User Data layer
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	/**
	 * find User by email
	 * @param email
	 * @return User
	 */
	Optional<User> findByEmail(String email);

}
