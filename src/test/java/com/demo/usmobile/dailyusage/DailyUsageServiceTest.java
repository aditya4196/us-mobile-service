package com.demo.usmobile.dailyusage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.demo.usmobile.MongoTestConfig;
import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.repository.DailyUsageRepository;
import com.demo.usmobile.service.CycleService;
import com.demo.usmobile.service.DailyUsageService;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class DailyUsageServiceTest {
	
    @Mock
    private DailyUsageRepository dailyUsageRepository;
    
    @Mock
    private CycleService cycleService;
    
    @InjectMocks
    private DailyUsageService dailyUsageService;

    @DisplayName("")
    @Test
    public void DailyUsageService_getCurrentCycleDailyUsage() throws ParseException, RecordNotFoundException {
    	
    	DailyUsage dailyUsage = DailyUsage.builder()
    			.userId("1")
    			.mdn("888")
    			.usageDate(null)
    			.usedInMb(100)
    			.build();
    	
    	when(cycleService.getCurrentCycle(any())).thenReturn(Mockito.mock(Cycle.class));
    	when(dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(
    			anyString(), anyString(), any(), any(), any())).thenReturn(Mockito.mock(Page.class));
    	
    	Page<DailyUsage> dailyUsagePage = dailyUsageService.getCurrentDailyUsageByUserIdAndMdn(Mockito.mock(DataRequestDTO.class), PageRequest.of(0, 1));
    	assertTrue(!dailyUsagePage.isEmpty());
    }
    
    
    
}
