package com.demo.usmobile.cycle;



import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.repository.CycleRepository;

@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class CycleRepositoryTest {
	
	@Autowired
	private CycleRepository cycleRepository;
	
	@DisplayName("find By User Id And Mdn And StartDate Less Than Equal And EndDate Greater Than Equal")
	@Test
	public void CycleRepository_findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual() throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date startDate = dateFormat.parse("2024-06-01");
		Date endDate = dateFormat.parse("2024-06-21");
		
		Cycle cycle = Cycle.builder()
				.userId("1")
				.mdn("888")
				.startDate(startDate)
				.endDate(endDate)
				.build();
		
		cycleRepository.save(cycle);
		Optional<Cycle> returnedCycle = cycleRepository.findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
				cycle.getUserId(), cycle.getMdn(), cycle.getStartDate(), cycle.getEndDate());
		
		Assertions.assertNotNull(returnedCycle);
		cycleRepository.delete(cycle);
	}
	
	@DisplayName("find All By UserId And Mdn")
	@Test
	public void CycleRepository_findAllByUserIdAndMdn() throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date startDate = dateFormat.parse("2024-06-01");
		Date endDate = dateFormat.parse("2024-06-21");
		
		Cycle cycle = Cycle.builder()
				.userId("1")
				.mdn("888")
				.startDate(startDate)
				.endDate(endDate)
				.build();
		
		cycleRepository.save(cycle);
		Page<Cycle> returnedCycle = cycleRepository.findAllByUserIdAndMdn(
				cycle.getUserId(), cycle.getMdn(), PageRequest.of(0, 1));
		
		Assertions.assertNotNull(returnedCycle);
		cycleRepository.delete(cycle);
		
	}
	
	@DisplayName("count By UserId And Mdn And EndDate Greater Than Equal")
	@Test
	public void CycleRepository_countByUserIdAndMdnAndEndDateGreaterThanEqual() throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date startDate = dateFormat.parse("2024-06-01");
		Date endDate = dateFormat.parse("2024-06-21");
		Date newStartDate = dateFormat.parse("2024-06-30");
		
		Cycle cycle = Cycle.builder()
				.userId("1")
				.mdn("888")
				.startDate(startDate)
				.endDate(endDate)
				.build();
		
		cycleRepository.save(cycle);
		Integer returnedCycle = cycleRepository.countByUserIdAndMdnAndEndDateGreaterThanEqual(
				cycle.getUserId(), cycle.getMdn(), newStartDate);
		
		assertThat(returnedCycle).isEqualTo(1);
		cycleRepository.delete(cycle);
		
	}

}
