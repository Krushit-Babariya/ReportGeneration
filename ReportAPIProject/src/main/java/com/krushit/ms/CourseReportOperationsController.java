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

import com.krushit.entity.CourseDetails;
import com.krushit.model.SearchInputs;
import com.krushit.model.SearchResults;
import com.krushit.service.ICourseMgmtService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/report-api")
@OpenAPIDefinition(info =
	@Info(
          title = "Course Report",
          version = "0.1",
          description = "Report Generation API",
          contact = @Contact(name = "Krushit Babariya", email = "krushitsbabariya44@gmail.com")
		)
)
public class CourseReportOperationsController {

	@Autowired
	private ICourseMgmtService service;

	@GetMapping("/courses")
	@Operation(summary = "Get Courses by Categories",
    responses = {
            @ApiResponse(description = "All Courses Categories",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Courses not available")})
	public ResponseEntity<?> fetchCourseCategories() {
		try {
			Set<String> coursesInfo = service.showAllCourseCategories();
			return new ResponseEntity<Set<String>>(coursesInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/training-modes")
	@Operation(summary = "Get All Training Modes",
    responses = {
            @ApiResponse(description = "All Training Modes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Training modes not available")})
	public ResponseEntity<?> fetchTrainingModes() {
		try {
			Set<String> trainingModeInfo = service.showAllTrainingModes();
			return new ResponseEntity<Set<String>>(trainingModeInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/faculties")
	@Operation(summary = "Get All Faculties",
    responses = {
            @ApiResponse(description = "All Faculties",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Faculties not available")})
	public ResponseEntity<?> fetchFaculties() {
		try {
			Set<String> facultiesInfo = service.showAllFaculties();
			return new ResponseEntity<Set<String>>(facultiesInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/search")
	@Operation(summary = "Search Courses by Filters",
    responses = {
            @ApiResponse(description = "Filtered Course Results",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResults.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search inputs"),
            @ApiResponse(responseCode = "404", description = "No courses found matching filters")})
	public ResponseEntity<?> fetchCoursesByFilters(@RequestBody SearchInputs inputs) {
		try {
			List<SearchResults> list = service.showAllResultsByFilters(inputs);
			return new ResponseEntity<List<SearchResults>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/pdf-report")
	@Operation(summary = "Generate PDF Report",
    responses = {
            @ApiResponse(description = "PDF Report generated and sent",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "400", description = "Invalid search inputs"),
            @ApiResponse(responseCode = "500", description = "Error generating PDF report")})
	public void showPdfReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			res.setContentType("application/pdf");
			res.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
			service.generatePdfReport(inputs, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/excel-report")
	@Operation(summary = "Generate Excel Report",
    responses = {
            @ApiResponse(description = "Excel Report generated and sent",
                    content = @Content(mediaType = "application/vnd.ms-excel")),
            @ApiResponse(responseCode = "400", description = "Invalid search inputs"),
            @ApiResponse(responseCode = "500", description = "Error generating Excel report")})
	public void showExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
			service.generateExcelReport(inputs, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/all-pdf-report")
	@Operation(summary = "Generate PDF Report for All Data",
    responses = {
            @ApiResponse(description = "PDF Report for all data generated and sent",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "500", description = "Error generating PDF report")})
	public void showPdfReportAllData(HttpServletResponse res) {
		try {
			res.setContentType("application/pdf");
			res.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
			service.generatePdfReportAllData(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/all-excel-report")
	@Operation(summary = "Generate Excel Report for All Data",
    responses = {
            @ApiResponse(description = "Excel Report for all data generated and sent",
                    content = @Content(mediaType = "application/vnd.ms-excel")),
            @ApiResponse(responseCode = "500", description = "Error generating Excel report")})
	public void showExcelReportAllData(HttpServletResponse res) {
		try {
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
			service.generateExcelReportAllData(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
