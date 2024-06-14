package com.demo.usmobile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "DailyUsage")
public class DailyUsageDTO {
	
	@Id
	private String id;
	
	private String userId;
	
	private String usageDate;
	
	private int usedInMb;

}
