package com.coder.demosisfo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Transactional
	Optional<Student> findByNoBp(Integer noBp);
	
	Optional<Student> findByPassword(String password);

}
