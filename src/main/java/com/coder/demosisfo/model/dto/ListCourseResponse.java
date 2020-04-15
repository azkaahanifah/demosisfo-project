package com.coder.demosisfo.model.dto;

import lombok.Data;

@Data
public class ListCourseResponse {
	private Integer id;
	private String namaFakultas;
	private String namaJurusan;
	private String namaMatakuliah;
	private Integer semester;
	private Integer sks;
	private Integer kuota;

}
