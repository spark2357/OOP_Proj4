package com.oop23.Proj4Team5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Proj4Team5Application {

	public static void main(String[] args) {
		SpringApplication.run(Proj4Team5Application.class, args);
	}

}
