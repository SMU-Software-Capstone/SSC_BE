package com.sithub.sithub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SithubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SithubApplication.class, args);
	}

}
