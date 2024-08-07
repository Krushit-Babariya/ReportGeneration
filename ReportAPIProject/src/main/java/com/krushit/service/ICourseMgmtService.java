package com.krushit.service;

import java.util.List;
import java.util.Set;

import com.krushit.model.SearchInputs;
import com.krushit.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

public interface ICourseMgmtService {
	public Set<String> showAllCourseCategories();
	public Set<String> showAllTrainingModes();
	public Set<String> showAllFaculties();
	
	public List<SearchResults> showAllResultsByFilters(SearchInputs inputs);
	
	public void generatePdfReport(SearchInputs inputs, HttpServletResponse res) throws Exception;
	public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) throws Exception;
}
