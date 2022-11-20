package com.capgemini.edem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DemoDgsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDgsApplication.class, args);
	}

}
