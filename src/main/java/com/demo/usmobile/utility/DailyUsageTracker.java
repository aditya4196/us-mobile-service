package com.demo.usmobile.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.usmobile.documents.DailyUsage;
import com.demo.usmobile.repository.DailyUsageRepository;

@Component
public class DailyUsageTracker {
	
	Random randobj = new Random();
	
	@Autowired
	private DailyUsageRepository dailyUsageRepository;
	
	public void insertDailyUsageData(String userId, String mdn, Date startDate, Date endDate) {
			
		Date currentDate = startDate;
	    while (currentDate.before(endDate)) {
	    	
	        saveData(currentDate, userId, mdn);

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentDate);
	        calendar.add(Calendar.DATE, 1);
	        currentDate = calendar.getTime();
	    }	
	}
	
	public void saveData(Date date, String userId, String mdn) {
		dailyUsageRepository.save(DailyUsage.builder()
				.userId(userId)
				.mdn(mdn)
				.usageDate(date)
				.usedInMb(randobj.nextInt(50,100))
				.build());
	}

}
