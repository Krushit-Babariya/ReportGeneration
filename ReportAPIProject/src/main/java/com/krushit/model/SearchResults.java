package com.krushit.model;

import java.time.LocalDateTime;
	
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchResults {
	private Integer courseID;
	private String courseName;
	private String facultyName;
	private String courseCategory;
	private String location;
	private Double fee;
	private String adminName;
	private Long adminContact;
	private String trainindMode;
	private LocalDateTime startDate;
	private String courseStatus;
}
