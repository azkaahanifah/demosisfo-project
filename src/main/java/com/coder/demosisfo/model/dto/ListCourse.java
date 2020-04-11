package com.coder.demosisfo.model.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ListCourse {
	private String ips;
	private Integer semester;
	private List<GetCourse> listCourse;

}
