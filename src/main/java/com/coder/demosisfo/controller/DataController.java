package com.coder.demosisfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.GetCourseRequest;
import com.coder.demosisfo.model.dto.GetCourseResponse;
import com.coder.demosisfo.model.dto.ListCourseResponse;
import com.coder.demosisfo.model.dto.CourseDto;
import com.coder.demosisfo.model.dto.MajorDto;
import com.coder.demosisfo.service.DataService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DataController {
	
	@Autowired
	DataService dataService;
	
	@PostMapping("/addFaculty")
	public ResponseEntity<String> postFaculty(@Valid @RequestBody FacultyDto facultyDto) throws BadRequestException{
		String postData = dataService.postNamaFakultas(facultyDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PostMapping("/addMajor")
	public ResponseEntity<String> postMajor(@Valid @RequestBody MajorDto majorDto) throws BadRequestException{
		String postData = dataService.postNamaJurusan(majorDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PostMapping("/addCourse")
	public ResponseEntity<ListCourseResponse> postCourse(@Valid @RequestBody CourseDto listCourseDto) throws BadRequestException{
		ListCourseResponse postData = dataService.postMatakuliah(listCourseDto);
		return new ResponseEntity<>(postData, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCourse/{id}")
	public ResponseEntity<ListCourseResponse> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseDto listCourseDto) throws BadRequestException{
		ListCourseResponse updateCourse = dataService.updateCourses(id, listCourseDto);
		return new ResponseEntity<>(updateCourse, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getListCourse")
	public ResponseEntity<List<GetCourseResponse>> getListCourse(@Valid @RequestBody GetCourseRequest request) throws BadRequestException{
		List<GetCourseResponse> getListCourseBySmt = dataService.getListCourseBySmt(request);
		return new ResponseEntity<>(getListCourseBySmt, HttpStatus.OK);
	}

}
