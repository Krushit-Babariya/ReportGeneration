package com.krushit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.krushit.entity.CourseDetails;
import com.krushit.model.SearchInputs;
import com.krushit.model.SearchResults;
import com.krushit.repository.ICourseDetailsRepository;

import jakarta.servlet.http.HttpServletResponse;

public class CourseMgmtImpl implements ICourseMgmtService {
	@Autowired
	private ICourseDetailsRepository repo;

	@Override
	public Set<String> showAllCourseCategories() {
		return repo.getUniqueCourseCategories();
	}

	@Override
	public Set<String> showAllTrainingModes() {
		return repo.getUniqueTrainingMode();
	}

	@Override
	public Set<String> showAllFaculties() {
		return repo.getUniqueFaculty();
	}

	/*@Override
	public List<SearchResults> showAllResultsByFilters(SearchInputs inputs) {

		CourseDetails entity = new CourseDetails();
		String category = inputs.getCourseCategory();
		if (category != null && !category.equals("") && category.length() != 0)
			entity.setCourseCategory(category);

		String facultyName = inputs.getFacultyName();
		if (facultyName != null && !facultyName.equals("") && facultyName.length() != 0)
			entity.setFacultyName(facultyName);

		String trainingMode = inputs.getTrainingMode();
		if (trainingMode != null && !trainingMode.equals("") && trainingMode.length() != 0)
			entity.setTrainingMode(trainingMode);

		LocalDateTime startDate = inputs.getStartsOn();
		if (startDate != null)
			entity.setStartDate(startDate);

		Example<CourseDetails> example = Example.of(entity);

		List<CourseDetails> listEntities = repo.findAll(example);
		List<SearchResults> listResults = new ArrayList<SearchResults>();

		listEntities.forEach(course -> {
			SearchResults res = new SearchResults();
			BeanUtils.copyProperties(course, res);
			listResults.add(res);
		});
		return listResults;
	}
	*/
	
	@Override
	public List<SearchResults> showAllResultsByFilters(SearchInputs inputs) {

		CourseDetails entity = new CourseDetails();
		String category = inputs.getCourseCategory();
		if (StringUtils.hasLength(category))
			entity.setCourseCategory(category);

		String facultyName = inputs.getFacultyName();
		if (StringUtils.hasLength(facultyName))
			entity.setFacultyName(facultyName);

		String trainingMode = inputs.getTrainingMode();
		if (StringUtils.hasLength(trainingMode))
			entity.setTrainingMode(trainingMode);

		LocalDateTime startDate = inputs.getStartsOn();
		if (!ObjectUtils.isEmpty(startDate))
			entity.setStartDate(startDate);

		Example<CourseDetails> example = Example.of(entity);

		List<CourseDetails> listEntities = repo.findAll(example);
		List<SearchResults> listResults = new ArrayList<SearchResults>();

		listEntities.forEach(course -> {
			SearchResults res = new SearchResults();
			BeanUtils.copyProperties(course, res);
			listResults.add(res);
		});
		return listResults;
	}

	@Override
	public void generatePdfReport(SearchInputs inputs, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

}
