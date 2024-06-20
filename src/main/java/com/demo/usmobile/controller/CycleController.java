package com.demo.usmobile.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.CycleCollisionException;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.service.CycleService;
import com.demo.usmobile.utility.DailyUsageTracker;

import jakarta.validation.Valid;

@RestController
@Validated
public class CycleController {
	
	@Autowired
	private DailyUsageTracker dailyUsageTracker;
	
	@Autowired
	private CycleService cycleService;

	@PostMapping("/subscribe")
	public ResponseEntity<Cycle> addCycle(final @RequestBody Cycle cycle) throws CycleCollisionException {
		Cycle insertedCycle = cycleService.subscribeBilling(cycle);
		dailyUsageTracker.insertDailyUsageData(cycle.getUserId(), cycle.getMdn(), cycle.getStartDate(), cycle.getEndDate());
		return new ResponseEntity<>(insertedCycle, HttpStatus.OK);
	}
	
	@GetMapping("/getCycles")
	public ResponseEntity<Page<Cycle>> getCycles(
			@RequestParam("size") Integer size,
			@RequestParam("page") Integer page,
			@RequestBody @Valid DataRequestDTO userRequest) throws RecordNotFoundException{
		return new ResponseEntity<>(
				cycleService.getAllCyclesByUserIdAndMdn(userRequest, PageRequest.of(page,size))
				, HttpStatus.OK);
	}
	


}
