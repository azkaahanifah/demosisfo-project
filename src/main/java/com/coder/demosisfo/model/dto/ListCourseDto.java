package com.coder.demosisfo.model.dto;

import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ListCourseDto {
	private Set<String> roles;
	private String namaFakultas;
	private String namaJurusan;
	private String namaMatakuliah;
	private Integer semester;
	private Integer sks;

}
