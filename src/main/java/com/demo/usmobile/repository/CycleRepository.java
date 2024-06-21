package com.demo.usmobile.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.documents.Cycle;


/**
 * Cycle Data Layer
 */
@Repository
public interface CycleRepository extends MongoRepository<Cycle, String>{
	
	/**
	 * find cycle By userId and mdn and currentDate between startDate and endDate of cycle
	 * @param userId
	 * @param mdn
	 * @param startDate
	 * @param endDate
	 * @return Optional<Cycle>
	 */
    Optional<Cycle> findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String userId, String mdn, Date startDate, Date endDate);
    
    /**
     * find cycles by userId and mdn
     * @param userId
     * @param mdn
     * @param pageable
     * @return Paginated Cycle Object
     */
    Page<Cycle> findAllByUserIdAndMdn(String userId, String mdn, Pageable pageable);
    
    /**
     * count of cycles by userid and mdn and end date greater than startDate of the new cycle
     * @param userId
     * @param mdn
     * @param startDate
     * @return Integer
     */
    Integer countByUserIdAndMdnAndEndDateGreaterThanEqual(
    		String userId, String mdn, Date startDate);
    
}
