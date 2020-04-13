package com.coder.demosisfo.service;

import java.util.List;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Faculty;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.Student;
import com.coder.demosisfo.model.dto.AddStudentRequest;
import com.coder.demosisfo.model.dto.UpdateDataStudentRequest;
import com.coder.demosisfo.model.dto.UpdateDataStudentResponse;

public interface StudentService {
	public Student addDataStudent(AddStudentRequest addStudentRequest) throws BadRequestException;
	public Faculty getFakultas(String namaFakultas) throws BadRequestException;
	public List<Major> getListMajor(String namaFakultas) throws BadRequestException;
	public Major getJurusan(String namaJurusan) throws BadRequestException;
	public boolean isNomorBpExist(Integer nomorBp);
	public boolean isPasswordExist(String password);
	public UpdateDataStudentResponse updateDataStudent(Long id, UpdateDataStudentRequest updateStudentRequest) throws BadRequestException;
	
}
