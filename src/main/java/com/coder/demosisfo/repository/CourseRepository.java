package com.coder.demosisfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.demosisfo.model.Courses;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer>{

}
