package com.demo.usmobile.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.repository.DailyUsageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DailyUsageService {
	
	@Autowired
	private CycleService cycleService;
	
	@Autowired
	private DailyUsageRepository dailyUsageRepository;

	/**
	 * 
	 * @param dataRequestDTO
	 * @param pageable
	 * @return
	 * @throws RecordNotFoundException
	 */
	public Page<DailyUsage> getCurrentDailyUsageByUserIdAndMdn(final DataRequestDTO dataRequestDTO, final Pageable pageable) throws RecordNotFoundException {
		
		final Cycle currentCycleObj = cycleService.getCurrentCycle(dataRequestDTO);
		log.info("Fetched current Cycle object successfully");
			
		final Page<DailyUsage> dailyUsagePages = dailyUsageRepository.findByUserIdAndMdnAndUsageDateBetween(dataRequestDTO.getUserId(), dataRequestDTO.getMdn(), currentCycleObj.getStartDate(), currentCycleObj.getEndDate(), pageable);
			
		if(dailyUsagePages.isEmpty()) {
			log.error("No daily Usage records for the cycle for userId {} and phone number {} ", dataRequestDTO.getUserId(), dataRequestDTO.getMdn());
			throw new RecordNotFoundException("No daily Usage records for the cycle for userId " + dataRequestDTO.getUserId() + " and phone number " + dataRequestDTO.getMdn());
		}			
		return dailyUsagePages;
	}
	
	

}
