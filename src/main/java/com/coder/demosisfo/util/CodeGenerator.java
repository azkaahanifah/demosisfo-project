package com.coder.demosisfo.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {
	public Integer generateNoBp(int a, int b) {
		LocalDate dateNow = LocalDate.now();
		int year = dateNow.getYear();
		String stringYear = Integer.toString(year);
		int value = ThreadLocalRandom.current().nextInt(9999);
		String formatted = String.format("%03d", value);
		String fakultasId = Integer.toString(a);
		String jurusanId = Integer.toString(b);
		String finalNoBp = stringYear.substring(2, 4) + fakultasId + jurusanId + formatted;
		int aa = Integer.parseInt(finalNoBp);
		return aa;
	}
	
	public String generatePassword() {
		int value = ThreadLocalRandom.current().nextInt(999);
		String formatted = String.format("%03d", value);
		String defaultName = "password";
		return defaultName + formatted;
	}
}
