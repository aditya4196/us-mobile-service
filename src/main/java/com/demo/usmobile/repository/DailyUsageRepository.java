package com.demo.usmobile.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.documents.DailyUsage;

/**
 * Daily Usage Data Layer
 */
@Repository
public interface DailyUsageRepository extends MongoRepository<DailyUsage, String>{

	/**
	 * find DailyUsage by user id and mdn and usage Date between start and end date of cycle
	 * @param userId
	 * @param mdn
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return Paginated Daily Usages
	 */
    Page<DailyUsage> findByUserIdAndMdnAndUsageDateBetween(
            String userId, String mdn, Date startDate, Date endDate, Pageable pageable);
}
