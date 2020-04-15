package com.coder.demosisfo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetCourseRequest {
	private String namaFakultas;
	private String namaJurusan;
	private Integer semester;

}
