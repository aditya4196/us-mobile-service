package com.demo.usmobile.dto;

import lombok.Data;

@Data
public class PageRequest {
	
	private int page;
	private int sizePerPage;
	private String sortField;
	private String sortDirection;
}
