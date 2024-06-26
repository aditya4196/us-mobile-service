package com.demo.usmobile.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.service.DailyUsageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/**
 * Daily Usage Controller Layer
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DailyUsageController {
	
	@Autowired
	private DailyUsageService dailyUsageService;
	
	/**
	 * endpoint to get the current cycle daily usage for user id and mdn
	 * @param size
	 * @param page
	 * @param userRequest
	 * @return Paginated Daily Usage Object
	 * @throws RecordNotFoundException
	 */
	@GetMapping("/getCurrentCycleDailyUsage")
	public ResponseEntity<?> getCurrentDailyUsage(
			@RequestParam("size") Integer size,
			@RequestParam("page") Integer page,
			@RequestBody @Valid DataRequestDTO userRequest) throws RecordNotFoundException{
		Pageable pageable = PageRequest.of(page,size);
		Page<DailyUsage> dailyUsageList = dailyUsageService.getCurrentDailyUsageByUserIdAndMdn(userRequest, pageable);
		return new ResponseEntity<Page<DailyUsage>>(dailyUsageList, HttpStatus.OK);
		
		
	}
	
	
	

}
