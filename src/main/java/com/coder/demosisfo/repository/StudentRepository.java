package com.coder.demosisfo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Transactional
	Optional<Student> findByNoBp(Integer noBp);
	
	Optional<Student> findByPassword(String password);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Mahasiswa SET daftar_matakuliah = :daftar_matakuliah WHERE id = :id", nativeQuery = true)
	void updateStudent(@Param("id") Long id, @Param("daftar_matakuliah") byte[] daftar_matakuliah);

}
