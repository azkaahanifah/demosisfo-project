package com.coder.demosisfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer>{
	
	Optional<Major> findByNamaJurusan(String namaJurusan);
	List<Major> findByNamaFakultas(String namaFakultas);

}
