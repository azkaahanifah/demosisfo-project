package com.coder.demosisfo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.controller.DataController;
import com.coder.demosisfo.exception.BadRequestException;
import com.coder.demosisfo.model.Courses;
import com.coder.demosisfo.model.DataRole;
import com.coder.demosisfo.model.EData;
import com.coder.demosisfo.model.Faculty;
import com.coder.demosisfo.model.Major;
import com.coder.demosisfo.model.dto.FacultyDto;
import com.coder.demosisfo.model.dto.ListCourseDto;
import com.coder.demosisfo.model.dto.MajorDto;
import com.coder.demosisfo.repository.CourseRepository;
import com.coder.demosisfo.repository.DataRoleRepository;
import com.coder.demosisfo.repository.FacultyRepository;
import com.coder.demosisfo.repository.MajorRepository;
import com.coder.demosisfo.service.DataService;
import com.coder.demosisfo.service.StudentService;

@Service("dataService")
public class DataServiceImpl implements DataService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
	
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
		LOGGER.info("Check role from request: {}", facultyDto.getRoles());
		Set<String> roles = facultyDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);
		LOGGER.info("Role success to find: {}", dataRoles);
		
		Faculty faculty = new Faculty(facultyDto.getNamaFakultas());
		faculty.setData(dataRoles);
		facultyRepository.save(faculty);
		LOGGER.info("Inserting nama fakultas to database is success");
		
		return "Data is success to submitted";
	}

	@Override
	public String postNamaJurusan(MajorDto majorDto) throws BadRequestException{
		LOGGER.info("Check role from request: {}", majorDto.getRoles());
		Set<String> roles = majorDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);
		LOGGER.info("Role success to find: {}", dataRoles);
		
		LOGGER.info("Check nama fakultas to database: {}", majorDto.getNamaFakultas() );
		Faculty faculty = studentService.getFakultas(majorDto.getNamaFakultas());
		
		Major major = new Major(majorDto.getNamaJurusan(), 
								   faculty.getNamaFakultas(), 
								   dataRoles);
		majorRepository.save(major);
		LOGGER.info("Inserting nama jurusan to database is success");
		
		return "Data is success to submitted";
	}
	
	@Override
	public String postMatakuliah(ListCourseDto listCourseDto) throws BadRequestException{
		LOGGER.info("Check role from request: {}", listCourseDto.getRoles());
		Set<String> roles = listCourseDto.getRoles();
		Set<DataRole> dataRoles = this.getDataRole(roles);
		LOGGER.info("Role success to find: {}", dataRoles);
		
		LOGGER.info("Check nama fakultas to major table: {}", listCourseDto.getNamaFakultas());
		List<Major> getListOfMajor = studentService.getListMajor(listCourseDto.getNamaFakultas());
		LOGGER.info("Get one row data from major table  by nama fakultas");
		Major getMajor = this.getOneRowOfMajor(getListOfMajor, listCourseDto.getNamaJurusan());
		
		Courses courses = new Courses(listCourseDto.getNamaMatakuliah(), 
									  listCourseDto.getSemester(),
									  listCourseDto.getSks(),
									  getMajor.getNamaFakultas(),
									  getMajor.getNamaJurusan(),
									  dataRoles);
		courseRepository.save(courses);
		LOGGER.info("Inserting nama matakuliah to database is success");
		
		return "Data is success to submitted";
	}
	
	public Major getOneRowOfMajor(List<Major> listOfMajor, String namaJurusan) throws BadRequestException{
		for(Major getMajor : listOfMajor) {
			String _namaJurusan = getMajor.getNamaJurusan();
			if (_namaJurusan.equalsIgnoreCase(namaJurusan)) {
				return getMajor;
			}
		}
		throw new BadRequestException("Error get one row of Major");
	}
	
	public Set<DataRole> getDataRole(Set<String> roles) {
		Set<DataRole> dataRoles = new HashSet<>();
		if (roles == null) {
			DataRole fakultasRole = dataRoleRepository.findByData(EData.FAKULTAS)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
			dataRoles.add(fakultasRole);
		} else {
			roles.forEach(role -> {
				switch (role) {
				case "jurusan":
					DataRole jurusanRole = dataRoleRepository.findByData(EData.JURUSAN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					dataRoles.add(jurusanRole);
					break;
				
				case "matakuliah":
					DataRole matkulRole = dataRoleRepository.findByData(EData.MATAKULIAH)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					dataRoles.add(matkulRole);
					break;

				default:
					DataRole fakultasRole = dataRoleRepository.findByData(EData.FAKULTAS)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					dataRoles.add(fakultasRole);
					break;
				}
			});
		}
		return dataRoles;
	}

	@Override
	public String updateCourses(Integer id, ListCourseDto listCourseDto) throws BadRequestException {
		Optional<Courses> getCourse = courseRepository.findById(id);
		if (getCourse.isPresent()) {
			Courses _courses = getCourse.get();
			_courses.setMatakuliahId(id);
			_courses.setNamaMatakuliah(listCourseDto.getNamaMatakuliah());
			_courses.setSemester(listCourseDto.getSemester());
			_courses.setSks(listCourseDto.getSks());
			_courses.setNamaFakultas(listCourseDto.getNamaFakultas());
			_courses.setNamaJurusan(listCourseDto.getNamaJurusan());
			courseRepository.save(_courses);
			LOGGER.info("Update courses to database");
			return "Update courses success";
		}
		LOGGER.error("Failed to update courses with id: {}", id);
		throw new BadRequestException("Failed to update courses!");
		
	}

}
