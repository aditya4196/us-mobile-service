package com.demo.usmobile.dailyusage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.repository.DailyUsageRepository;

@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class DailyUsageRepositoryTest {
	
	@Autowired
	private DailyUsageRepository dailyUsageRepository;
	
	
	@DisplayName("find By UserId And Mdn And UsageDate Between")
	@Test
	public void DailyUsageRepository_findByUserIdAndMdnAndUsageDateBetween() throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date startDate = dateFormat.parse("2024-06-01");
		Date endDate = dateFormat.parse("2024-06-21");
		Date usageDate = dateFormat.parse("2024-06-10");
		
		DailyUsage dailyUsage = DailyUsage.builder()
				.userId("1")
				.mdn("999")
				.usageDate(usageDate)
				.build();
		
		dailyUsageRepository.save(dailyUsage);
		Page<DailyUsage> returnedDailyUsage = dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(
				dailyUsage.getUserId(), dailyUsage.getMdn(), startDate, endDate, PageRequest.of(0, 1));
		
		Assertions.assertNotNull(returnedDailyUsage);
	}
	
	

}
