package com.daylight.devleague;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableAsync
public class DevLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevLeagueApplication.class, args);
	}

}
