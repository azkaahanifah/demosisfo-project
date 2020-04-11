package com.coder.demosisfo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Faculty;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.Student;
import com.coder.demosisfo.model.dto.AddStudentRequest;
import com.coder.demosisfo.model.dto.UpdateDataStudentRequest;
import com.coder.demosisfo.repository.CourseRepository;
import com.coder.demosisfo.repository.FacultyRepository;
import com.coder.demosisfo.repository.MajorRepository;
import com.coder.demosisfo.repository.StudentRepository;
import com.coder.demosisfo.service.DataService;
import com.coder.demosisfo.service.StudentService;
import com.coder.demosisfo.util.CodeGenerator;
import com.coder.demosisfo.util.RegexUtil;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

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
	CourseRepository courseRepository;

	@Autowired
	DataService dataService;

	@Autowired
	RegexUtil regexUtil;
	
	@Autowired
	EntityManager em;

	@Override
	public Student addDataStudent(AddStudentRequest addStudentRequest) throws BadRequestException {
		LOGGER.info("Check nama fakultas to major table: {}", addStudentRequest.getNamaFakultas());
		List<Major> getListOfMajor = this.getListMajor(addStudentRequest.getNamaFakultas());
		LOGGER.info("Get one row data from major table by nama fakultas");
		Major getMajor = dataService.getOneRowOfMajor(getListOfMajor, addStudentRequest.getNamaJurusan());

		LOGGER.info("Validate DOB");
		this.validateDataStudentRequest(addStudentRequest);

		LOGGER.info("Generate nomor BP");
		Faculty getFaculty = this.getFakultas(addStudentRequest.getNamaFakultas());
		Integer nomorBp = codeGenerator.generateNoBp(getFaculty.getFakultasId(), getMajor.getJurusanId());
		while (isNomorBpExist(nomorBp)) {
			nomorBp = codeGenerator.generateNoBp(getFaculty.getFakultasId(), getMajor.getJurusanId());
			LOGGER.info("Nomor BP is success to generate");
		}

		LOGGER.info("Generate password");
		String password = codeGenerator.generatePassword();
		while (isPasswordExist(password)) {
			password = codeGenerator.generatePassword();
			LOGGER.info("Password is success to generate");
		}

		Student postDataStudent = new Student();
		postDataStudent.setNoBp(nomorBp);
		postDataStudent.setPassword(password);
		postDataStudent.setNamaMhs(addStudentRequest.getNamaMhs());
		postDataStudent.setDob(addStudentRequest.getDob());
		postDataStudent.setGender(addStudentRequest.getGender());
		postDataStudent.setNamaFakultas(getMajor.getNamaFakultas());
		postDataStudent.setNamaJurusan(getMajor.getNamaJurusan());
		postDataStudent.setSemester(addStudentRequest.getSemester());

		studentRepository.saveAndFlush(postDataStudent);
		LOGGER.info("Inserting data to Student : {}", postDataStudent);
		return postDataStudent;
	}

	@Override
	public Faculty getFakultas(String namaFakultas) throws BadRequestException {
		Optional<Faculty> getNamaFakultas = facultyRepository.findByNamaFakultas(namaFakultas);
		if (getNamaFakultas.isPresent()) {
			return getNamaFakultas.get();
		}
		LOGGER.error("Cannot find Faculty: {}", namaFakultas);
		throw new BadRequestException("Cannot find Faculty: " + namaFakultas);
	}

	@Override
	public Major getJurusan(String namaJurusan) throws BadRequestException {
		Optional<Major> gettNamaJurusan = majorRepository.findByNamaJurusan(namaJurusan);
		if (gettNamaJurusan.isPresent()) {
			return gettNamaJurusan.get();
		}
		LOGGER.error("Cannot find Major: {}", namaJurusan);
		throw new BadRequestException("Cannot find Major: " + namaJurusan);
	}

	@Override
	public List<Major> getListMajor(String namaFakultas) throws BadRequestException {
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
		try {
			return getNomorBp.isPresent();
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean isPasswordExist(String password) {
		Optional<Student> getPassword = studentRepository.findByPassword(password);
		try {
			return getPassword.isPresent();
		} catch (Exception e) {
			return false;
		}
		
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

	@Override
	public Student updateDataStudent(UpdateDataStudentRequest updateStudentRequest) throws BadRequestException {
		return null;
	}
	
}
