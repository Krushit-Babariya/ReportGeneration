package com.krushit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

	}

	@Override
	public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) {
		//get search results
		List<SearchResults> list = showAllResultsByFilters(inputs);

		//create work book
		HSSFWorkbook workBook = new HSSFWorkbook();
		//create sheet
		HSSFSheet sheet1 = workBook.createSheet("CourseDetails");
		//create row
		HSSFRow headerRow = sheet1.createRow(0);
		headerRow.createCell(0).setCellValue("Course ID");
		headerRow.createCell(1).setCellValue("Course Name");
		headerRow.createCell(2).setCellValue("Faculty Name");
		headerRow.createCell(3).setCellValue("Course Category");
		headerRow.createCell(4).setCellValue("Location");
		headerRow.createCell(5).setCellValue("Fee");
		headerRow.createCell(6).setCellValue("Admin Name");
		headerRow.createCell(7).setCellValue("Admin Contact");
		headerRow.createCell(8).setCellValue("Training Mode");
		headerRow.createCell(9).setCellValue("Start Date");
		headerRow.createCell(10).setCellValue("Course Status");
	}

}
