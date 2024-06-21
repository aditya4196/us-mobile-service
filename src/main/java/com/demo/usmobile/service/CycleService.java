package com.demo.usmobile.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.usmobile.documents.Cycle;
import com.demo.usmobile.documents.User;
import com.demo.usmobile.dto.DataRequestDTO;
import com.demo.usmobile.exception.CycleCollisionException;
import com.demo.usmobile.exception.RecordNotFoundException;
import com.demo.usmobile.exception.UserNotFoundException;
import com.demo.usmobile.repository.CycleRepository;
import com.demo.usmobile.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CycleService {
	
    @Autowired
	private CycleRepository cycleRepository;
	
    @Autowired
	private UserRepository userRepository;
	
	/**
	 * 
	 * @param userRequest
	 * @return
	 * @throws RecordNotFoundException
	 */
	public Cycle getCurrentCycle(final DataRequestDTO userRequest) throws RecordNotFoundException {
		final Date currentDate = new Date();
		return getCurrentCycleByDate(userRequest, currentDate);
	}
	
	public Cycle getCurrentCycleByDate(final DataRequestDTO userRequest,final Date currentDate) throws RecordNotFoundException {

		final Optional<Cycle> cycleDTO = cycleRepository.findByUserIdAndMdnAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userRequest.getUserId(), userRequest.getMdn(), currentDate, currentDate);
		if(!cycleDTO.isPresent()) {
			log.error("No Billing Cycle for userId {} and Phone Number {} found",userRequest.getUserId(), userRequest.getMdn());
			throw new RecordNotFoundException("No Billing Cycle for userId " + userRequest.getUserId() +" and Phone Number "+ userRequest.getMdn() +" found");
		}
		log.info("Current Cycle fetched successfully : {}", cycleDTO.toString());
		return cycleDTO.get();
	}
	
	/**
	 * 
	 * @param userRequest
	 * @return
	 * @throws RecordNotFoundException
	 */
	public Page<Cycle> getAllCyclesByUserIdAndMdn(final DataRequestDTO userRequest, Pageable pageable) throws RecordNotFoundException{
		Page<Cycle> cycles = cycleRepository.findAllByUserIdAndMdn(userRequest.getUserId(), userRequest.getMdn(), pageable);
			
		if(cycles.isEmpty()) {
			log.error("No Cycles found for the user Id {} and phone number {}",userRequest.getUserId(), userRequest.getMdn());
			throw new RecordNotFoundException("No billing cycles present for user Id "+userRequest.getUserId()+" and phone number " + userRequest.getMdn());
		}
		log.info("Cycles fetched successfully {}", cycles);
		return cycles;
	}

	/**
	 * 
	 * @param cyclegetUserId
	 * @return
	 * @throws CycleCollisionException
	 */
	public Cycle subscribeBilling(final Cycle cycle) throws CycleCollisionException {
		if(!userRepository.existsById(cycle.getUserId())) {
			log.error("User with userId {} not found to subscribe for billing", cycle.getUserId());
			throw new UserNotFoundException("User with userId not found to subscribe for billing");
		}

		if(cycleRepository.countByUserIdAndMdnAndEndDateGreaterThanEqual(
				cycle.getUserId(), cycle.getMdn(), cycle.getStartDate()) > 0) {
			log.error("New cycle for user id {} and mdn {}, start date is less than the ongoing cycle end date",cycle.getUserId(),cycle.getMdn());
			throw new CycleCollisionException("New cycle for user id "+cycle.getUserId()+" mdn "+cycle.getMdn()+ " start date is less than the ongoing cycle end date");
		}	
		log.info("cycle object to be added successfully");
		return cycleRepository.save(cycle);
	}
	

}
