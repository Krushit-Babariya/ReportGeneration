package com.krushit.service;

import java.awt.Color;
import java.io.IOException;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.krushit.entity.CourseDetails;
import com.krushit.model.SearchInputs;
import com.krushit.model.SearchResults;
import com.krushit.repository.ICourseDetailsRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
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

	/*
	 * @Override public List<SearchResults> showAllResultsByFilters(SearchInputs
	 * inputs) {
	 * 
	 * CourseDetails entity = new CourseDetails(); String category =
	 * inputs.getCourseCategory(); if (category != null && !category.equals("") &&
	 * category.length() != 0) entity.setCourseCategory(category);
	 * 
	 * String facultyName = inputs.getFacultyName(); if (facultyName != null &&
	 * !facultyName.equals("") && facultyName.length() != 0)
	 * entity.setFacultyName(facultyName);
	 * 
	 * String trainingMode = inputs.getTrainingMode(); if (trainingMode != null &&
	 * !trainingMode.equals("") && trainingMode.length() != 0)
	 * entity.setTrainingMode(trainingMode);
	 * 
	 * LocalDateTime startDate = inputs.getStartsOn(); if (startDate != null)
	 * entity.setStartDate(startDate);
	 * 
	 * Example<CourseDetails> example = Example.of(entity);
	 * 
	 * List<CourseDetails> listEntities = repo.findAll(example); List<SearchResults>
	 * listResults = new ArrayList<SearchResults>();
	 * 
	 * listEntities.forEach(course -> { SearchResults res = new SearchResults();
	 * BeanUtils.copyProperties(course, res); listResults.add(res); }); return
	 * listResults; }
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
	public void generatePdfReport(SearchInputs inputs, HttpServletResponse res) throws DocumentException, IOException {
		// get resultset
		List<SearchResults> list = showAllResultsByFilters(inputs);
		// craete document object
		Document doc = new Document(PageSize.A3);
		// get PdfWriter to to write into document and response object
		PdfWriter.getInstance(doc, res.getOutputStream());
		// open document
		doc.open();

		// define font for paragraph
		Font font = FontFactory.getFont("Alata");
		font.setSize(30);

		// create paragraph having content
		Paragraph para = new Paragraph("Report", font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		doc.add(para);

		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.8f, 0.8f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });
		table.setSpacingBefore(0.5f);

		// prepare heading rows cell in pdf table
		PdfPCell cell = new PdfPCell();
		cell.setBorder(5);
		cell.setPadding(5);
		Font cellFont = FontFactory.getFont("Alata");
		cellFont.setColor(Color.BLACK);
		cell.setPhrase(new Phrase("Course ID", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Faculty Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Category", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Location", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Fee", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Contact", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Training Mode", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Start Date", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Status", cellFont));
		table.addCell(cell);

		// add data cells to pdf table
		list.forEach(result -> {
			table.addCell(String.valueOf(result.getCourseID()));
			table.addCell(result.getCourseName());
			table.addCell(result.getFacultyName());
			table.addCell(result.getCourseCategory());
			table.addCell(result.getLocation());
			table.addCell(String.valueOf(result.getFee()));
			table.addCell(result.getAdminName());
			table.addCell(String.valueOf(result.getAdminContact()));
			table.addCell(result.getTrainindMode());
			table.addCell(String.valueOf(result.getStartDate()));
			table.addCell(result.getCourseStatus());
		});

		doc.add(table);
		doc.close();
	}

	/*
	 * public void generatePdfReport(SearchInputs inputs, HttpServletResponse res)
	 * throws DocumentException, IOException { // Get result set List<SearchResults>
	 * list = showAllResultsByFilters(inputs);
	 * 
	 * // Create document object Document doc = new Document(PageSize.A3);
	 * 
	 * // Get PdfWriter to write into document and response object
	 * PdfWriter.getInstance(doc, res.getOutputStream());
	 * 
	 * // Open document doc.open();
	 * 
	 * // Define font for paragraph Font font = FontFactory.getFont("Helvetica"); //
	 * Changed to a standard font font.setSize(30);
	 * 
	 * // Create paragraph with content Paragraph para = new Paragraph("Report",
	 * font); para.setAlignment(Paragraph.ALIGN_CENTER); doc.add(para);
	 * 
	 * // Create a table with 11 columns PdfPTable table = new PdfPTable(11);
	 * table.setWidthPercentage(100); table.setWidths(new float[] {0.8f, 0.8f, 1.0f,
	 * 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
	 * table.setSpacingBefore(5.0f);
	 * 
	 * // Define the header font Font headerFont = FontFactory.getFont("Helvetica");
	 * headerFont.setSize(12); headerFont.setColor(Color.BLACK);
	 * 
	 * // Prepare header rows for the PDF table table.addCell(new PdfPCell(new
	 * Phrase("Course ID", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Course Name", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Faculty Name", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Course Category", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Location", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Fee", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Admin Name", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Admin Contact", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Training Mode", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Start Date", headerFont))); table.addCell(new PdfPCell(new
	 * Phrase("Course Status", headerFont)));
	 * 
	 * // Add data cells to the PDF table list.forEach(result -> {
	 * table.addCell(String.valueOf(result.getCourseID()));
	 * table.addCell(result.getCourseName());
	 * table.addCell(result.getFacultyName());
	 * table.addCell(result.getCourseCategory());
	 * table.addCell(result.getLocation());
	 * table.addCell(String.valueOf(result.getFee()));
	 * table.addCell(result.getAdminName());
	 * table.addCell(String.valueOf(result.getAdminContact()));
	 * table.addCell(result.getTrainindMode());
	 * table.addCell(String.valueOf(result.getStartDate()));
	 * table.addCell(result.getCourseStatus()); });
	 * 
	 * // Add table to the document doc.add(table);
	 * 
	 * // Close the document doc.close(); }
	 */

	@Override
	public void generateExcelReport(SearchInputs inputs, HttpServletResponse res) throws Exception {
		// get search results
		List<SearchResults> list = showAllResultsByFilters(inputs);

		// create work book
		HSSFWorkbook workBook = new HSSFWorkbook();
		// create sheet
		HSSFSheet sheet1 = workBook.createSheet("CourseDetails");
		// create row
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

		// add data rows
		int i = 1;
		for (SearchResults result : list) {
			HSSFRow dataRow = sheet1.createRow(i);
			dataRow.createCell(0).setCellValue(result.getCourseID());
			dataRow.createCell(1).setCellValue(result.getCourseName());
			dataRow.createCell(2).setCellValue(result.getFacultyName());
			dataRow.createCell(3).setCellValue(result.getCourseCategory());
			dataRow.createCell(4).setCellValue(result.getLocation());
			dataRow.createCell(5).setCellValue(result.getFee());
			dataRow.createCell(6).setCellValue(result.getAdminName());
			dataRow.createCell(7).setCellValue(result.getAdminContact());
			dataRow.createCell(8).setCellValue(result.getTrainindMode());
			dataRow.createCell(9).setCellValue(result.getStartDate());
			dataRow.createCell(10).setCellValue(result.getCourseStatus());
			i++;
		}
		;

		// get OutputStream pointing to the response object
		ServletOutputStream outputStream = res.getOutputStream();
		// write workbook data to response object
		workBook.write(outputStream);
		outputStream.close();
		workBook.close();
	}

	@Override
	public void generateExcelReportAllData(HttpServletResponse res) throws Exception {
		// get All the records from Db table
		List<CourseDetails> list = repo.findAll();

		// copy List<CourseDetails> to List<SearchResults>
		List<SearchResults> listResults = new ArrayList<>();
		list.forEach(course -> {
			SearchResults result = new SearchResults();
			BeanUtils.copyProperties(course, result);
			listResults.add(result);
		});

		// create work book
		HSSFWorkbook workBook = new HSSFWorkbook();
		// create sheet
		HSSFSheet sheet1 = workBook.createSheet("CourseDetails");
		// create row
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

		// add data rows
		int i = 1;
		for (SearchResults result : listResults) {
			HSSFRow dataRow = sheet1.createRow(i);
			dataRow.createCell(0).setCellValue(result.getCourseID());
			dataRow.createCell(1).setCellValue(result.getCourseName());
			dataRow.createCell(2).setCellValue(result.getFacultyName());
			dataRow.createCell(3).setCellValue(result.getCourseCategory());
			dataRow.createCell(4).setCellValue(result.getLocation());
			dataRow.createCell(5).setCellValue(result.getFee());
			dataRow.createCell(6).setCellValue(result.getAdminName());
			dataRow.createCell(7).setCellValue(result.getAdminContact());
			dataRow.createCell(8).setCellValue(result.getTrainindMode());
			dataRow.createCell(9).setCellValue(result.getStartDate());
			dataRow.createCell(10).setCellValue(result.getCourseStatus());
			i++;
		}
		;

		// get OutputStream pointing to the response object
		ServletOutputStream outputStream = res.getOutputStream();
		// write workbook data to response object
		workBook.write(outputStream);
		outputStream.close();
		workBook.close();
	}

	@Override
	public void generatePdfReportAllData(HttpServletResponse res) throws Exception {
		// get All the records from Db table
		List<CourseDetails> list = repo.findAll();

		// copy List<CourseDetails> to List<SearchResults>
		List<SearchResults> listResults = new ArrayList<>();
		list.forEach(course -> {
			SearchResults result = new SearchResults();
			BeanUtils.copyProperties(course, result);
			listResults.add(result);
		});

		// create Document obj (openPdf)
		Document document = new Document(PageSize.A3);

		// get PdfWriter to write to the document and response obj
		PdfWriter.getInstance(document, res.getOutputStream());

		// open the document
		document.open();

		// Define Font for the Paragraph
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);

		// create the paragraph having content and above font style
		Paragraph para = new Paragraph("Search Report of Courses", font);
		para.setAlignment(Paragraph.ALIGN_CENTER);

		// add paragraph to document
		document.add(para);

		// Display search results as the pdf table
		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(90);
		table.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
		table.setSpacingBefore(2.0f);

		// prepare heading row cells in the pdf table
		PdfPCell cell = new PdfPCell();
		cell.setBorder(5);
		cell.setPadding(5);
		Font cellFont = FontFactory.getFont("Alata");
		cellFont.setColor(Color.BLACK);
		cell.setPhrase(new Phrase("Course ID", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Faculty Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Category", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Location", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Fee", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Name", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Admin Contact", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Training Mode", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Start Date", cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Course Status", cellFont));
		table.addCell(cell);

		// add data cells to pdf table
		list.forEach(result -> {
			table.addCell(String.valueOf(result.getCourseID()));
			table.addCell(result.getCourseName());
			table.addCell(result.getFacultyName());
			table.addCell(result.getCourseCategory());
			table.addCell(result.getLocation());
			table.addCell(String.valueOf(result.getFee()));
			table.addCell(result.getAdminName());
			table.addCell(String.valueOf(result.getAdminContact()));
			table.addCell(result.getTrainingMode());
			table.addCell(String.valueOf(result.getStartDate()));
			table.addCell(result.getCourseStatus());
		});

		// add table to document
		document.add(table);

		// close the document
		document.close();

	}

}
