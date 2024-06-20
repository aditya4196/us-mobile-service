package com.demo.usmobile.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.usmobile.documents.DailyUsage;

@Repository
public interface DailyUsageRepository extends MongoRepository<DailyUsage, String>{

    Page<DailyUsage> findByUserIdAndMdnAndUsageDateBetween(
            String userId, String mdn, Date startDate, Date endDate, Pageable pageable);
}
