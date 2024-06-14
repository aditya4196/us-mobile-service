package com.demo.usmobile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.usmobile.model.DailyUsageDTO;

public interface DailyUsageRepository extends MongoRepository<DailyUsageDTO, String>{

}
