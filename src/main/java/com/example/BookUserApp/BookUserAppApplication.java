package com.example.BookUserApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.BookUserApp.model")
public class BookUserAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookUserAppApplication.class, args);
	}

}
