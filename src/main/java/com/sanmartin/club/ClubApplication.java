package com.sanmartin.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages={"com.sanmartin.club"})
public class ClubApplication {

		public static void main(String[] args) throws Throwable {
			SpringApplication.run(ClubApplication.class, args);
		}

	}
	
	


