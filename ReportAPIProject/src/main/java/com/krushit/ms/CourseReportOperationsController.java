package com.krushit.ms;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushit.model.SearchInputs;
import com.krushit.model.SearchResults;
import com.krushit.service.ICourseMgmtService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/report-api")
public class CourseReportOperationsController {
	@Autowired
	private ICourseMgmtService service;

	@GetMapping("/courses")
	public ResponseEntity<?> fetchCourseCategories() {
		try {
			Set<String> coursesInfo = service.showAllCourseCategories();
			return new ResponseEntity<Set<String>>(coursesInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/training-modes")
	public ResponseEntity<?> fetchTrainingModes() {
		try {
			Set<String> trainingModeInfo = service.showAllTrainingModes();
			return new ResponseEntity<Set<String>>(trainingModeInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/faculties")
	public ResponseEntity<?> fetchFaculties() {
		try {
			Set<String> facultiesInfo = service.showAllFaculties();
			return new ResponseEntity<Set<String>>(facultiesInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> fetchCoursesByFilters(@RequestBody SearchInputs inputs) {
		try {
			List<SearchResults> list = service.showAllResultsByFilters(inputs);
			return new ResponseEntity<List<SearchResults>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
 
	@PostMapping("/pdf-report")
	public void showPdfReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			// set the response content type
			res.setContentType("application/pdf");
			// set the content-disposition header to response content going to browser as
			// downloadable file
			res.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
			service.generatePdfReport(inputs, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// method

	@PostMapping("/excel-report")
	public void showExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			// set the response content type
			res.setContentType("application/vnd.ms-excel");
			// set the content-disposition header to response content going to browser as
			// downloadable file
			res.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
			service.generateExcelReport(inputs, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// method

	/*
	 * @GetMapping("/all-pdf-report") public void
	 * showPdfReportAllData(HttpServletResponse res) { try { // set the response
	 * content type res.setContentType("application/pdf"); // set the
	 * content-disposition header to response content going to browser as //
	 * downloadable file res.setHeader("Content-Disposition",
	 * "attachment;fileName=courses.pdf"); // use service
	 * service.generatePdfReport(res); } catch (Exception e) { e.printStackTrace();
	 * } }// method
	 * 
	 * @GetMapping("/all-excel-report") public void
	 * showExcelReportAllData(HttpServletResponse res) { try { // set the response
	 * content type res.setContentType("application/vnd.ms-excel"); // set the
	 * content-disposition header to response content going to browser as //
	 * downloadable file res.setHeader("Content-Disposition",
	 * "attachment;fileName=courses.xls"); // use the service
	 * service.generateExcelReport(res); } catch (Exception e) {
	 * e.printStackTrace(); } }// method
	 */
}
