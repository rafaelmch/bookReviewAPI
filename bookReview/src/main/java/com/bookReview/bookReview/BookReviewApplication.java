package com.bookReview.bookReview;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title       = "Book Review API",
				version     = "1.0",
				description = "CRUD + filtering for books",
				contact     = @Contact(name="Raf", email="rafaelmch@gmail.com")
		),
		servers = @Server(url = "http://localhost:8081")
)
public class BookReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookReviewApplication.class, args);
	}

}
