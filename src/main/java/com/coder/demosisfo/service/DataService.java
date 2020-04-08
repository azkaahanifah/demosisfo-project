package com.coder.demosisfo.service;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.ListCourseDto;
import com.coder.demosisfo.model.dto.MajorDto;

public interface DataService {
	public String postNamaFakultas(FacultyDto facultyDto) throws BadRequestException;
	public String postNamaJurusan(MajorDto majorDto) throws BadRequestException;
	public String postMatakuliah(ListCourseDto listCourseDto) throws BadRequestException;
	public String updateCourses(Integer id, ListCourseDto listCourseDto) throws BadRequestException;

}
