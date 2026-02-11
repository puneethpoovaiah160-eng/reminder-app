package com.example.emay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmayApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmayApplication.class, args);
	}

}
