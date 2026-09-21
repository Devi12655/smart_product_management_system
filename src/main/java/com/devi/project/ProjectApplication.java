package com.devi.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication /*(
	exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
)*/
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	

}
