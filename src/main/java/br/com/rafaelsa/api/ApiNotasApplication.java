package br.com.rafaelsa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ApiNotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiNotasApplication.class, args);
	}
}
