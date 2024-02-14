package com.hss.rinhabackend2024q1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.hss.rinhabackend2024q1.persistence")
@EntityScan(basePackages = "com.hss.rinhabackend2024q1.persistence.model")
public class RinhaBackend2024Q1Application {

	public static void main(String[] args) {
		SpringApplication.run(com.hss.rinhabackend2024q1.RinhaBackend2024Q1Application.class, args);
	}

}
