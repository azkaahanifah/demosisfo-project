package com.coder.demosisfo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetCourse {
	private String namaMatakuliah;
	private Integer sks;
	private String nilai;
	private String grade;

}
