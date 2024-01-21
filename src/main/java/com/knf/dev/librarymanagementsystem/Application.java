package com.knf.dev.librarymanagementsystem;

import com.knf.dev.librarymanagementsystem.entity.*;
import com.knf.dev.librarymanagementsystem.repository.UserRepository;
import com.knf.dev.librarymanagementsystem.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class Application {


	private final BCryptPasswordEncoder passwordEncoder;


	private final BookService bookService;


	private final UserRepository userRepository;


	public Application(BCryptPasswordEncoder passwordEncoder, BookService bookService, UserRepository userRepository){
		this.passwordEncoder = passwordEncoder;
		this.bookService = bookService;
		this.userRepository = userRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate() {
		return (args) -> {

			var book = new Book("AP1287", "Spring in Action ", "CXEF12389", "Book description");
			book.addAuthors(new Author("Matt", "dummy description"));
			book.addCategories(new Category("Dummy categary"));
			book.addPublishers(new Publisher("Dummy publisher"));
			bookService.createBook(book);

			var book1 = new Book("BP567#R", "Spring Microservices", "KCXEF12389", "Description1");
			book1.addAuthors(new Author("Maxwell", "Test description1"));
			book1.addCategories(new Category("New category"));
			book1.addPublishers(new Publisher("publisher2"));
			bookService.createBook(book1);

			var book2 = new Book("GH67F#", "Spring Boot", "UV#JH", "description2");
			book2.addAuthors(new Author("Josh Lang", "Test description2"));
			book2.addCategories(new Category("Spring category"));
			book2.addPublishers(new Publisher("publisher3"));
			bookService.createBook(book2);

			var user = new User("admin", "admin", "admin@admin.in", passwordEncoder.encode("Temp123"),
					Arrays.asList(new Role("ROLE_ADMIN")));
			userRepository.save(user);

		};
	}
}
