package com.containerdepot.metcon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class MetconApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetconApplication.class, args);
	}

}
