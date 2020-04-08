package com.coder.demosisfo.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {
	public Integer generateNoBp(Integer a, Integer b) {
		LocalDate dateNow = LocalDate.now();
		int year = dateNow.getYear();
		String stringYear = Integer.toString(year);
		int month = dateNow.getMonthValue();
		String stringMonth = Integer.toString(month);
		if (stringMonth.length() == 1) {
			stringMonth = "0" + stringMonth;
		}
		int date = dateNow.getDayOfMonth();
		String stringDate = Integer.toString(date);
		if (stringDate.length() == 1) {
			stringDate = "0" + stringDate;
		}
		int value = ThreadLocalRandom.current().nextInt(9999);
		String formatted = String.format("%04d", value);
		String finalNoBp = stringYear.substring(2, 4) + stringMonth + stringDate  + a + b + formatted;
		return Integer.parseInt(finalNoBp);
	}
	
	public String generatePassword() {
		int value = ThreadLocalRandom.current().nextInt(999);
		String formatted = String.format("03d", value);
		String defaultName = "password";
		return defaultName + formatted;
	}
}
