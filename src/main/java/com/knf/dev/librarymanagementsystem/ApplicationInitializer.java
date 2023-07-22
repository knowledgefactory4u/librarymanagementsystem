package com.knf.dev.librarymanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.entity.User;
import com.knf.dev.librarymanagementsystem.repository.UserRepository;
import com.knf.dev.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final BookService bookService;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationInitializer(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeBooks();
        initializeUsers();
    }

    private void initializeBooks() throws IOException {
        List<Book> books = readBooksFromFile("books.json");
        for (Book book : books) {
            bookService.createBook(book);
        }
    }

    private void initializeUsers() throws IOException {
        List<User> users = readUsersFromFile("users.json");
        userRepository.saveAll(users);
    }

    private List<Book> readBooksFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = new ClassPathResource(filePath).getFile().toPath();
        byte[] jsonData = Files.readAllBytes(path);
        return Arrays.asList(objectMapper.readValue(jsonData, Book[].class));
    }

    private List<User> readUsersFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = new ClassPathResource(filePath).getFile().toPath();
        byte[] jsonData = Files.readAllBytes(path);
        return Arrays.asList(objectMapper.readValue(jsonData, User[].class));
    }
}
