package com.lineragency.metcon_tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetconTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetconTasksApplication.class, args);
	}

}
