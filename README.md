# Course Report Generation API

## Overview

The Course Report Generation API is a Spring Boot application designed to manage and generate reports for courses. It supports operations to fetch course categories, training modes, faculties, and search courses based on various filters. Additionally, it allows the generation of PDF and Excel reports for filtered and all data.

## Features

- Fetch all course categories
- Retrieve all training modes
- Get all faculties
- Search courses based on filters
- Generate PDF and Excel reports for filtered courses
- Generate PDF and Excel reports for all courses

## Technologies Used

- **Spring Boot**: Framework for building the REST API
- **Apache POI**: Library for handling Excel file generation
- **Open PDF API**: Library for handling PDF file generation
- **OpenAPI**: Documentation of API using Swagger
- **Java**: Programming language

## Endpoints

### Course Categories

- **GET** `/report-api/courses`
  - Fetches all available course categories.
  - **Response**: `200 OK` with a set of course categories.

### Training Modes

- **GET** `/report-api/training-modes`
  - Retrieves all available training modes.
  - **Response**: `200 OK` with a set of training modes.

### Faculties

- **GET** `/report-api/faculties`
  - Retrieves all available faculties.
  - **Response**: `200 OK` with a set of faculties.

### Search Courses

- **GET** `/report-api/search`
  - Searches for courses based on filters provided in the request body.
  - **Request Body**: JSON object with search criteria (course category, faculty name, training mode, start date).
  - **Response**: `200 OK` with a list of courses matching the criteria.
  - **Response Codes**:
    - `400 Bad Request` for invalid inputs
    - `404 Not Found` if no courses match the filters

### Generate PDF Report

- **POST** `/report-api/pdf-report`
  - Generates a PDF report for courses matching the filters provided in the request body.
  - **Request Body**: JSON object with search criteria.
  - **Response**: PDF file containing the report.

### Generate Excel Report

- **POST** `/report-api/excel-report`
  - Generates an Excel report for courses matching the filters provided in the request body.
  - **Request Body**: JSON object with search criteria.
  - **Response**: Excel file containing the report.

### Generate PDF Report for All Data

- **GET** `/report-api/all-pdf-report`
  - Generates a PDF report for all courses.
  - **Response**: PDF file containing the report.

### Generate Excel Report for All Data

- **GET** `/report-api/all-excel-report`
  - Generates an Excel report for all courses.
  - **Response**: Excel file containing the report.


### API Documentation
The API documentation is available at http://localhost:4040/swagger-ui.html.
