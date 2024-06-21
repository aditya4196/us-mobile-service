package com.demo.usmobile.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.CycleCollisionException;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.CycleRepository;
import com.demo.usmobile.repository.UserRepository;
import com.demo.usmobile.service.CycleService;

@DataMongoTest
@Testcontainers
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = MongoTestConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class CycleServiceTest {
	
	@Mock
	private CycleRepository cycleRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private CycleService cycleService;
	
	@DisplayName("get Current Cycle By Date")
	@Test
	public void CycleService_getCurrentCycleByDate_Success() throws RecordNotFoundException {
		
		DataRequestDTO request = new DataRequestDTO("1","888");
		when(cycleRepository.findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
				anyString(), anyString(), any(), any())).thenReturn(Optional.of(Mockito.mock(Cycle.class)));
		
		Cycle returnedCycle = cycleService.getCurrentCycleByDate(request, new Date());
		Assertions.assertNotNull(returnedCycle);	
	}
	
	
	@DisplayName("get Current Cycle By Date throws record not found exception")
	@Test
	public void CycleService_getCurrentCycleByDate_throwsRecordNotFoundException() throws RecordNotFoundException {
		
		DataRequestDTO request = new DataRequestDTO("1","888");
		when(cycleRepository.findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
				anyString(), anyString(), any(), any())).thenReturn(Optional.ofNullable(null));
		
		Assertions.assertThrows(RecordNotFoundException.class, () -> cycleService.getCurrentCycleByDate(request, new Date()));	
	}
	
	  @DisplayName("get all Cycles")
	    @Test
	    public void CycleService_getAllCycles_Success() throws ParseException, RecordNotFoundException {
	    	
	    	DataRequestDTO dataRequestDTO = new DataRequestDTO("user1", "mdn1");
	        Pageable pageable = PageRequest.of(0, 10);

	        Cycle cycle = new Cycle();
	        Page<Cycle> cyclePage = new PageImpl<>(Collections.singletonList(cycle));
 
	        when(cycleRepository.findAllByUserIdAndMdn(
	                anyString(), anyString(), any())).thenReturn(cyclePage);

	        // Call the method under test
	        Page<Cycle> result = cycleService.getAllCyclesByUserIdAndMdn(dataRequestDTO, PageRequest.of(0,1));

	        // Verify results
	        assertNotNull(result);
	        assertEquals(1, result.getContent().size());
	        assertEquals(cycle, result.getContent().get(0));
	    }
	    
	    
	    @DisplayName("Get Current Daily Usage throws no records found")
	    @Test
	    public void CycleService_getCurrentDailyUsage_throwsRecordNotFoundException() throws RecordNotFoundException {
	        DataRequestDTO dataRequestDTO = new DataRequestDTO("user1", "mdn1");
	        Pageable pageable = PageRequest.of(0, 10);

	        when(cycleRepository.findAllByUserIdAndMdn(
	                anyString(), anyString(), any())).thenReturn(Page.empty());

	        Assertions.assertThrows(RecordNotFoundException.class, () -> {
	            cycleService.getAllCyclesByUserIdAndMdn(dataRequestDTO, PageRequest.of(0,1));
	        });
	    }
	
	    @DisplayName("Add a cycle")
	    @Test
	    public void CycleService_subscribeBilling_Success() throws CycleCollisionException {
	    	
	    	Cycle cycleobj = Cycle.builder()
	    			.userId("1")
	    			.mdn("888")
	    			.startDate(new Date())
	    			.endDate(new Date())
	    			.build();
	    	
	    	when(userRepository.existsById(anyString())).thenReturn(true);
	    	when(cycleRepository.countByUserIdAndMdnAndEndDateGreaterThanEqual(
	    			anyString(), anyString(), any())).thenReturn(0);
	    	when(cycleRepository.save(any())).thenReturn(Mockito.mock(Cycle.class));
	    	
	    	Cycle cycle = cycleService.subscribeBilling(cycleobj);
	    	Assertions.assertNotNull(cycle);
	    }
	    
	    @DisplayName("Add a cycle throws UserNotFoundException")
	    @Test
	    public void CycleService_subscribeBilling_throwsUserNotFound(){
	    	
	    	Cycle cycleobj = Cycle.builder()
	    			.userId("1")
	    			.mdn("888")
	    			.startDate(new Date())
	    			.endDate(new Date())
	    			.build();
	    	
	    	when(userRepository.existsById(anyString())).thenReturn(false);
	    	when(cycleRepository.countByUserIdAndMdnAndEndDateGreaterThanEqual(
	    			anyString(), anyString(), any())).thenReturn(0);
	    	when(cycleRepository.save(any())).thenReturn(Mockito.mock(Cycle.class));
	    	
	    	Assertions.assertThrows(UserNotFoundException.class, () -> cycleService.subscribeBilling(cycleobj));
	    }
	    
	    @DisplayName("Add a cycle throws CycleCollisionException")
	    @Test
	    public void CycleService_subscribeBilling_throwsCycleCollision() throws CycleCollisionException {
	    	
	    	Cycle cycleobj = Cycle.builder()
	    			.userId("1")
	    			.mdn("888")
	    			.startDate(new Date())
	    			.endDate(new Date())
	    			.build();
	    	
	    	when(userRepository.existsById(anyString())).thenReturn(true);
	    	when(cycleRepository.countByUserIdAndMdnAndEndDateGreaterThanEqual(
	    			anyString(), anyString(), any())).thenReturn(1);
	    	when(cycleRepository.save(any())).thenReturn(Mockito.mock(Cycle.class));
	    	
	    	Assertions.assertThrows(CycleCollisionException.class, () -> cycleService.subscribeBilling(cycleobj));
	    }
	

}
