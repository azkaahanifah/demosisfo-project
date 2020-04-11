package com.coder.demosisfo.model.dto;

import lombok.Data;

@Data
public class GetCourseResp {
	private Integer matakuliahId;
	private String namaMatakuliah;
	private Integer semester;
	private Integer sks;
	private Integer kuota;

}
