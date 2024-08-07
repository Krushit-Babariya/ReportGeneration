package com.krushit.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COURCE_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetails {
	@Id
	@GeneratedValue
	private Integer courseID;
	@Column(length = 30)
	private String courseName;
	@Column(length = 100)
	
	private String facultyName;
	@Column(length = 30)
	private String location;
	@Column(length = 30)
	private String courseCategory;
	private Double fee;
	
	@Column(length = 30)
	private String adminName;
	private Long adminContact;
	
	@Column(length = 30)
	private String trainingMode;
	private LocalDateTime startDate;
	@Column(length = 30)
	private String courseStatus;
	
	@CreationTimestamp
	@Column(insertable = true, updatable = false)
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false, updatable = true)
	private LocalDateTime updationDate;
	
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
}
