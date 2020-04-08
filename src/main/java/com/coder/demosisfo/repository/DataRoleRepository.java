package com.coder.demosisfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.DataRole;
import com.coder.demosisfo.model.EData;

@Repository
public interface DataRoleRepository extends JpaRepository<DataRole, Long>{
	
	Optional<DataRole> findByData(EData data);
}
