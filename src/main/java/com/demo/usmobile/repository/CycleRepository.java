package com.demo.usmobile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.usmobile.model.CycleDTO;

public interface CycleRepository extends MongoRepository<CycleDTO, String>{

}
