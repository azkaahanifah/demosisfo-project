package com.coder.demosisfo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConverterUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public byte[] convertDtoByte(Object object) {
		byte[] dtoContent = null;
		try {
			dtoContent = mapper.writeValueAsBytes(dtoContent);
		} catch (JsonProcessingException e) {
			LOGGER.error("Failed when processing json");
		}
		return dtoContent;
	}

}
