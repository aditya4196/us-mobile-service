package com.demo.usmobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DailyUsageSortField {
	USER_ID("userId"),
	MDN("mdn"),
	USAGE_DATE("usageDate"),
	USED_IN_MB("usedInMb");
	
	private final String fieldName;
	
	
}
