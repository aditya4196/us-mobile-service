package com.demo.usmobile.dailyusage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.DailyUsageRepository;
import com.demo.usmobile.service.CycleService;
import com.demo.usmobile.service.DailyUsageService;


@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class DailyUsageServiceTest {
	
    @Mock
    private DailyUsageRepository dailyUsageRepository;
    
    @Mock
    private CycleService cycleService;
    
    @InjectMocks
    private DailyUsageService dailyUsageService;

    @DisplayName("get the current cycle daily usage")
    @Test
    public void DailyUsageService_getCurrentCycleDailyUsage_Success() throws ParseException, RecordNotFoundException {
    	
    	DataRequestDTO dataRequestDTO = new DataRequestDTO("user1", "mdn1");
        Pageable pageable = PageRequest.of(0, 10);

        DailyUsage dailyUsage = new DailyUsage();
        Page<DailyUsage> dailyUsagePage = new PageImpl<>(Collections.singletonList(dailyUsage));

        when(cycleService.getCurrentCycle(dataRequestDTO)).thenReturn(Mockito.mock(Cycle.class));
        when(dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(
                anyString(), anyString(), any(), any(), any()
        )).thenReturn(dailyUsagePage);

        // Call the method under test
        Page<DailyUsage> result = dailyUsageService.getCurrentDailyUsageByUserIdAndMdn(dataRequestDTO, pageable);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(dailyUsage, result.getContent().get(0));
    }
    
    
    @DisplayName("Get Current Daily Usage throws no records found")
    @Test
    public void DailyUsageService_getCurrentDailyUsage_throwsRecordNotFoundException() throws RecordNotFoundException {
        DataRequestDTO dataRequestDTO = new DataRequestDTO("user1", "mdn1");
        Pageable pageable = PageRequest.of(0, 10);

        // Define mock behavior
        when(cycleService.getCurrentCycle(dataRequestDTO)).thenReturn(Mockito.mock(Cycle.class));
        when(dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(
                anyString(), anyString(), any(), any(), any()
        )).thenReturn(Page.empty());

        // Call the method under test and verify exception
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            dailyUsageService.getCurrentDailyUsageByUserIdAndMdn(dataRequestDTO, pageable);
        });
    }
    
    
    
}
