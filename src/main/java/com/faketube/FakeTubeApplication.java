package com.faketube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FakeTubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakeTubeApplication.class, args);
	}

}
