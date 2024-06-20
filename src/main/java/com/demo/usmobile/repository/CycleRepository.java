package com.demo.usmobile.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.documents.Cycle;


@Repository
public interface CycleRepository extends MongoRepository<Cycle, String>{
	
    Optional<Cycle> findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String userId, String mdn, Date startDate, Date endDate);
    
    Page<Cycle> findAllByUserIdAndMdn(String userId, String mdn, Pageable pageable);
    
    Integer countByUserIdAndMdnAndEndDateGreaterThanEqual(
    		String userId, String mdn, Date startDate);
    
}
