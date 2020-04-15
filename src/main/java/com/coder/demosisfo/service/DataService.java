package com.coder.demosisfo.service;

import java.util.List;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Courses;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.GetCourseRequest;
import com.coder.demosisfo.model.dto.GetCourseResponse;
import com.coder.demosisfo.model.dto.ListCourseResponse;
import com.coder.demosisfo.model.dto.CourseDto;
import com.coder.demosisfo.model.dto.MajorDto;

public interface DataService {
	public String postNamaFakultas(FacultyDto facultyDto) throws BadRequestException;
	public String postNamaJurusan(MajorDto majorDto) throws BadRequestException;
	public ListCourseResponse postMatakuliah(CourseDto listCourseDto) throws BadRequestException;
	public ListCourseResponse updateCourses(Integer id, CourseDto listCourseDto) throws BadRequestException;
	public Major getOneRowOfMajor(List<Major> listOfMajor, String namaJurusan) throws BadRequestException;
	public List<GetCourseResponse> getListCourseBySmt(GetCourseRequest getCourse) throws BadRequestException;
	public List<Courses> getListCourse(String namaFakultas, String namaJurusan) throws BadRequestException;
	public List<GetCourseResponse> getCourseBySmt(List<Courses> getListCourse, Integer semester);

}
