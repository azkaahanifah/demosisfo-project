package com.coder.demosisfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coder.demosisfo"})
public class SpringDemosisfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemosisfoApplication.class, args);
	}

}
