package com.coder.demosisfo.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateDataStudentRequest {
	private Integer noBp;
	private String namaMhs;
	private Date dob;
	private String gender;
	private String namaFakultas;
	private String namaJurusan;
	private Integer semester;
	private List<ListCourse> courses;

}
