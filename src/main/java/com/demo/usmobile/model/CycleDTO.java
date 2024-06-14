package com.demo.usmobile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection="Cycle")
public class CycleDTO {
	
	@Id
	private String id;
	
	private String mdn;
	
	private String startDate;
	
	private String endDate;
	
	private String userId;

}
