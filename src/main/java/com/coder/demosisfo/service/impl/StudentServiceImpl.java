package com.coder.demosisfo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Faculty;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.Student;
import com.coder.demosisfo.model.dto.AddStudentRequest;
import com.coder.demosisfo.repository.FacultyRepository;
import com.coder.demosisfo.repository.MajorRepository;
import com.coder.demosisfo.repository.StudentRepository;
import com.coder.demosisfo.service.StudentService;
import com.coder.demosisfo.util.CodeGenerator;
import com.coder.demosisfo.util.RegexUtil;

@Service("studentService")
public class StudentServiceImpl implements StudentService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	CodeGenerator codeGenerator;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@Autowired
	MajorRepository majorRepository;
	
	@Autowired
	RegexUtil regexUtil;
	
	@Override
	public Student addDataStudent(AddStudentRequest addStudentRequest) throws BadRequestException{
		Faculty namaFakultas = this.getFakultas(addStudentRequest.getNamaFakultas());
		Major namaJurusan = this.getJurusan(addStudentRequest.getNamaJurusan());
		
		LOGGER.info("Validate DOB");
		this.validateDataStudentRequest(addStudentRequest);
		
		LOGGER.info("Generate nomor BP");
		Integer nomorBp = codeGenerator.generateNoBp(namaFakultas.getFakultasId(), namaJurusan.getJurusanId());
		while(isNomorBpExist(nomorBp)) {
			nomorBp = codeGenerator.generateNoBp(namaFakultas.getFakultasId(), namaJurusan.getJurusanId());
			LOGGER.info("Nomor BP is success to generate");
		}
		
		LOGGER.info("Generate password");
		String password = codeGenerator.generatePassword();
		while(isPasswordExist(password)) {
			password = codeGenerator.generatePassword();
			LOGGER.info("Password is success to generate");
		}
		
		Student postDataStudent = new Student();
		postDataStudent.setNoBp(nomorBp);
		postDataStudent.setPassword(password);
		postDataStudent.setNamaMhs(addStudentRequest.getNamaMhs());
		postDataStudent.setDob(addStudentRequest.getDob());
		postDataStudent.setGender(addStudentRequest.getGender());
		postDataStudent.setNamaFakultas(namaFakultas.getNamaFakultas());
		postDataStudent.setNamaJurusan(namaJurusan.getNamaJurusan());
		postDataStudent.setSemester(addStudentRequest.getSemester());
		
		studentRepository.saveAndFlush(postDataStudent);
		LOGGER.info("Inserting data to Student : {}", postDataStudent);
		return postDataStudent;
	}

	@Override
	public Faculty getFakultas(String namaFakultas) throws BadRequestException{
		Optional<Faculty> getNamaFakultas = facultyRepository.findByNamaFakultas(namaFakultas);
		if (getNamaFakultas.isPresent()) {
			return getNamaFakultas.get();
		} 
		LOGGER.error("Cannot find Faculty: {}", namaFakultas);
		throw new BadRequestException("Cannot find Faculty: " + namaFakultas);
	}
	
	@Override
	public Major getJurusan(String namaJurusan) throws BadRequestException{
		Optional<Major> gettNamaJurusan = majorRepository.findByNamaJurusan(namaJurusan);
		if (gettNamaJurusan.isPresent()) {
			return gettNamaJurusan.get();
		} 
		LOGGER.error("Cannot find Major: {}", namaJurusan);
		throw new BadRequestException("Cannot find Major: " + namaJurusan);
	}
	
	@Override
	public List<Major> getListMajor(String namaFakultas) throws BadRequestException{
		List<Major> listOfMajor = majorRepository.findByNamaFakultas(namaFakultas);
		if (!listOfMajor.isEmpty()) {
			return listOfMajor;
		}
		LOGGER.error("Cannot find list of Major by Faculty: {}", namaFakultas);
		throw new BadRequestException("Cannot find list of Major by Faculty: {}" + namaFakultas);
	}

	@Override
	public boolean isNomorBpExist(Integer nomorBp) {
		Optional<Student> getNomorBp = studentRepository.findByNoBp(nomorBp);
		if (getNomorBp.isPresent()) {
			LOGGER.info("Re-generate nomor BP");
		}
		return true;
	}

	@Override
	public boolean isPasswordExist(String password) {
		Optional<Student> getPassword = studentRepository.findByPassword(password);
		if (getPassword.isPresent()) {
			LOGGER.info("Re-generate Password");
		}
		return true;
	}
	
	private boolean validateDataStudentRequest(AddStudentRequest addStudentRequest) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dob = dateFormat.format(addStudentRequest.getDob());
		String regexDob = regexUtil.matchesDob();
		if (!dob.matches(regexDob)) {
			LOGGER.error("Please insert DOB with yyyy-MM-dd format");
			
		}
		return !dob.isEmpty() || dob.matches(regexDob);
	}
	

}
