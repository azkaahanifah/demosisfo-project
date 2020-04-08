package com.coder.demosisfo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.ListCourseDto;
import com.coder.demosisfo.model.dto.MajorDto;
import com.coder.demosisfo.service.DataService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DataController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
	
	@Autowired
	DataService dataService;
	
	@PostMapping("/addFaculty")
	public ResponseEntity<?> postFaculty(@Valid @RequestBody FacultyDto facultyDto) throws BadRequestException{
		LOGGER.info("Fetching data: {}", facultyDto);
		String postData = dataService.postNamaFakultas(facultyDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PostMapping("/addMajor")
	public ResponseEntity<?> postMajor(@Valid @RequestBody MajorDto majorDto) throws BadRequestException{
		LOGGER.info("Fetching data: {}", majorDto);
		String postData = dataService.postNamaJurusan(majorDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PostMapping("/addCourse")
	public ResponseEntity<?> postCourse(@Valid @RequestBody ListCourseDto listCourseDto) throws BadRequestException{
		LOGGER.info("Fetching data: {}", listCourseDto);
		String postData = dataService.postMatakuliah(listCourseDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCourse/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable Integer id, @Valid @RequestBody ListCourseDto listCourseDto) throws BadRequestException{
		LOGGER.info("Fetching data: {}", listCourseDto);
		String updateCourse = dataService.updateCourses(id, listCourseDto);
		return new ResponseEntity<>(updateCourse, HttpStatus.ACCEPTED);
	}

}
