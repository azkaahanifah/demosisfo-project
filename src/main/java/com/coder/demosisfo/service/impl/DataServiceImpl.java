package com.coder.demosisfo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.constant.LoggerMsg;
import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Courses;
import com.coder.demosisfo.model.DataRole;
import com.coder.demosisfo.model.EData;
import com.coder.demosisfo.model.Faculty;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.GetCourseRequest;
import com.coder.demosisfo.model.dto.GetCourseResponse;
import com.coder.demosisfo.model.dto.ListCourseResponse;
import com.coder.demosisfo.model.dto.CourseDto;
import com.coder.demosisfo.model.dto.MajorDto;
import com.coder.demosisfo.repository.CourseRepository;
import com.coder.demosisfo.repository.DataRoleRepository;
import com.coder.demosisfo.repository.FacultyRepository;
import com.coder.demosisfo.repository.MajorRepository;
import com.coder.demosisfo.service.DataService;
import com.coder.demosisfo.service.StudentService;

@Service("dataService")
public class DataServiceImpl implements DataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);
	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	DataRoleRepository dataRoleRepository;

	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	MajorRepository majorRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentService studentService;

	@Override
	public String postNamaFakultas(FacultyDto facultyDto) {
		Set<String> roles = facultyDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);

		Faculty faculty = new Faculty(facultyDto.getNamaFakultas());
		faculty.setData(dataRoles);
		facultyRepository.save(faculty);
		LOGGER.info("Inserting nama fakultas to database is success");

		return LoggerMsg.ACTION_1;
	}

	@Override
	public String postNamaJurusan(MajorDto majorDto) throws BadRequestException {
		Set<String> roles = majorDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);

		LOGGER.info("Check nama fakultas to database: {}", majorDto.getNamaFakultas());
		Faculty faculty = studentService.getFakultas(majorDto.getNamaFakultas());

		Major major = new Major(majorDto.getNamaJurusan(), faculty.getNamaFakultas(), dataRoles);
		majorRepository.save(major);
		LOGGER.info("Inserting nama jurusan to database is success");

		return LoggerMsg.ACTION_1;
	}

	@Override
	public ListCourseResponse postMatakuliah(CourseDto listCourseDto) throws BadRequestException {
		Set<String> roles = listCourseDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);

		LOGGER.info("Check nama fakultas to major table: {}", listCourseDto.getNamaFakultas());
		List<Major> getListOfMajor = studentService.getListMajor(listCourseDto.getNamaFakultas());
		LOGGER.info("Get one row data from major table by nama fakultas");
		Major getMajor = this.getOneRowOfMajor(getListOfMajor, listCourseDto.getNamaJurusan());

		Courses courses = new Courses(listCourseDto.getNamaMatakuliah(), 
										listCourseDto.getSemester(),
										listCourseDto.getSks(), 
										listCourseDto.getKuota(), 
										getMajor.getNamaFakultas(), 
										getMajor.getNamaJurusan(),
										dataRoles);
		courseRepository.save(courses);
		LOGGER.info("Inserting nama matakuliah to database is success");
		
		return modelMapper.map(courses, ListCourseResponse.class);
	}

	@Override
	public Major getOneRowOfMajor(List<Major> listOfMajor, String namaJurusan) throws BadRequestException {
		for (Major getMajor : listOfMajor) {
			String jurusan = getMajor.getNamaJurusan();
			if (jurusan.equalsIgnoreCase(namaJurusan)) {
				return getMajor;
			}
		}
		throw new BadRequestException("Error get one row of Major");
	}

	public Set<DataRole> getDataRole(Set<String> roles) {
		Set<DataRole> dataRoles = new HashSet<>();
		if (roles == null) {
			DataRole fakultasRole = dataRoleRepository.findByData(EData.FAKULTAS)
					.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
			dataRoles.add(fakultasRole);
		} else {
			roles.forEach(role -> {
				switch (role) {
				case "jurusan":
					DataRole jurusanRole = dataRoleRepository.findByData(EData.JURUSAN)
							.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
					dataRoles.add(jurusanRole);
					break;

				case "matakuliah":
					DataRole matkulRole = dataRoleRepository.findByData(EData.MATAKULIAH)
							.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
					dataRoles.add(matkulRole);
					break;

				default:
					DataRole fakultasRole = dataRoleRepository.findByData(EData.FAKULTAS)
							.orElseThrow(() -> new RuntimeException(LoggerMsg.ERROR_1));
					dataRoles.add(fakultasRole);
					break;
				}
			});
		}
		return dataRoles;
	}

	@Override
	public ListCourseResponse updateCourses(Integer id, CourseDto listCourseDto) throws BadRequestException {
		Optional<Courses> getCourse = courseRepository.findById(id);
		if (getCourse.isPresent()) {
			Courses courses = getCourse.get();
			courses.setMatakuliahId(id);
			courses.setNamaMatakuliah(listCourseDto.getNamaMatakuliah());
			courses.setSemester(listCourseDto.getSemester());
			courses.setSks(listCourseDto.getSks());
			courses.setKuota(listCourseDto.getKuota());
			courses.setNamaFakultas(listCourseDto.getNamaFakultas());
			courses.setNamaJurusan(listCourseDto.getNamaJurusan());
			courseRepository.save(courses);
			LOGGER.info("Update courses to database");
			
			return modelMapper.map(courses, ListCourseResponse.class);
		}
		LOGGER.error("Failed to update courses with id: {}", id);
		throw new BadRequestException("Failed to update courses!");
	}

	@Override
	public List<GetCourseResponse> getListCourseBySmt(GetCourseRequest request) throws BadRequestException {
		List<Courses> getListCourse = this.getListCourse(request.getNamaFakultas(), request.getNamaJurusan());
		return this.getCourseBySmt(getListCourse, request.getSemester());
	}

	@Override
	public List<Courses> getListCourse(String namaFakultas, String namaJurusan) throws BadRequestException {
		List<Courses> listOfCourse = courseRepository.findByNamaFakultasAndNamaJurusan(namaFakultas, namaJurusan);
		if (!listOfCourse.isEmpty()) {
			return listOfCourse;
		}
		LOGGER.error("Cannot find list of Course");
		throw new BadRequestException("Cannot find list of Course");
	}

	@Override
	public List<GetCourseResponse> getCourseBySmt(List<Courses> getListCourse, Integer semester) {
		List<GetCourseResponse> getCourseBySmt = new ArrayList<>();
		GetCourseResponse getResp;
		for (Courses courses : getListCourse) {
			getResp = new GetCourseResponse();
			if (((semester % 2) == 0 && (courses.getSemester() % 2) == 0)
					|| ((semester % 2) == 1 && (courses.getSemester() % 2) == 1)) {
				getResp.setMatakuliahId(courses.getMatakuliahId());
				getResp.setNamaMatakuliah(courses.getNamaMatakuliah());
				getResp.setSemester(courses.getSemester());
				getResp.setSks(courses.getSks());
				getResp.setKuota(courses.getKuota());
				getCourseBySmt.add(getResp);
			}
		}
		return getCourseBySmt;
	}

}
