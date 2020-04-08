package com.coder.demosisfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer>{
	
	Optional<Faculty> findByNamaFakultas(String namaFakultas);

}
