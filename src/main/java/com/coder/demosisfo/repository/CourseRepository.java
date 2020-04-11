package com.coder.demosisfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Courses;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer>{
	
	@Query(value = "SELECT * FROM Matakuliah m WHERE m.nama_fakultas=?1 AND m.nama_jurusan=?2 ORDER BY m.id ASC", nativeQuery = true)
	List<Courses> findByNamaFakultasAndNamaJurusan(String namaFakultas, String namaJurusan);
	
	@Query(value = "SELECT * FROM Matakuliah m WHERE MOD (m.semester, 2)=0", nativeQuery = true)
	List<Courses> findByEvenSemester(Integer semester);
	
	@Query(value = "SELECT * FROM Matakuliah m WHERE MOD (m.semester, 2)=1", nativeQuery = true)
	List<Courses> findByOddSemester(Integer semester);

}
