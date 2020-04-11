package com.coder.demosisfo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Student;
import com.coder.demosisfo.model.dto.AddStudentRequest;
import com.coder.demosisfo.service.StudentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@PostMapping("/addatastudent")
	public ResponseEntity<?> addDataStudent(@Valid @RequestBody AddStudentRequest addStudentRequest) throws BadRequestException{
		Student student = studentService.addDataStudent(addStudentRequest);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	
}
