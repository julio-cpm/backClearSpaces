package br.com.api.clearSpaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class
ClearSpacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClearSpacesApplication.class, args);
	}

}
