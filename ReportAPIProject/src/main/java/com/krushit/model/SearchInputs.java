package com.krushit.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInputs {
	private String courseCategory;
	private String trainingMode;
	private String facultyName;
	private LocalDateTime startsOn;
}
