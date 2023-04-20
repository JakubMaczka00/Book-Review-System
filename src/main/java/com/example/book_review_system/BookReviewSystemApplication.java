package com.example.book_review_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication

public class BookReviewSystemApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(BookReviewSystemApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
