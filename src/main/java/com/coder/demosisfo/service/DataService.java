package com.coder.demosisfo.service;

import java.util.List;

import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Courses;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.GetCourseReq;
import com.coder.demosisfo.model.dto.GetCourseResp;
import com.coder.demosisfo.model.dto.ListCourseDto;
import com.coder.demosisfo.model.dto.MajorDto;

public interface DataService {
	public String postNamaFakultas(FacultyDto facultyDto) throws BadRequestException;
	public String postNamaJurusan(MajorDto majorDto) throws BadRequestException;
	public String postMatakuliah(ListCourseDto listCourseDto) throws BadRequestException;
	public String updateCourses(Integer id, ListCourseDto listCourseDto) throws BadRequestException;
	public Major getOneRowOfMajor(List<Major> listOfMajor, String namaJurusan) throws BadRequestException;
	public List<GetCourseResp> getListCourseBySmt(GetCourseReq getCourse) throws BadRequestException;
	public List<Courses> getListCourse(String namaFakultas, String namaJurusan) throws BadRequestException;
	public List<GetCourseResp> getCourseBySmt(List<Courses> getListCourse, Integer semester);

}
