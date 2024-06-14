package com.demo.usmobile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.model.UserDTO;

@Repository
public interface UsersRepository extends MongoRepository<UserDTO, String> {

}
