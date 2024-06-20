package com.demo.usmobile.cycle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.repository.CycleRepository;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class CycleRepositoryTest {
	
	@Autowired
	private CycleRepository cycleRepository;
	
	@DisplayName("")
	@Test
	public void CycleRepository_findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual() {
		
	}	
}
