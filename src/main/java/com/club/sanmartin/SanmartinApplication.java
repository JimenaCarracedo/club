package com.club.sanmartin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.club.sanmartin")

@EnableJpaRepositories("com.club.sanmartin.repository")
public class SanmartinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanmartinApplication.class, args);
	}

}
