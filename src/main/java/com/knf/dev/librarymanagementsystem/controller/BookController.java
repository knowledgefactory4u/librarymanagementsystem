package com.knf.dev.librarymanagementsystem.controller;

import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.service.AuthorService;
import com.knf.dev.librarymanagementsystem.service.BookService;
import com.knf.dev.librarymanagementsystem.service.CategoryService;
import com.knf.dev.librarymanagementsystem.service.PublisherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class BookController {

	final BookService bookService;
	final AuthorService authorService;
	final CategoryService categoryService;
	final PublisherService publisherService;
	private static String REDIR_BOOKS = "redirect:/books";
	private static String A_BOOK = "book";
	private static String BOOKS = "books";
	public BookController(PublisherService publisherService, CategoryService categoryService, BookService bookService,
			AuthorService authorService) {
		this.authorService = authorService;
		this.bookService = bookService;
		this.categoryService = categoryService;
		this.publisherService = publisherService;
	}

	@RequestMapping(path={ "/books", "/" },method= RequestMethod.GET)
	public String findAllBooks(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);

		var bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute(BOOKS, bookPage);

		var totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-books";
	}

	@RequestMapping(path="/searchBook",method= RequestMethod.GET)
	public String searchBook(@Param("keyword") String keyword, Model model) {

		model.addAttribute(BOOKS, bookService.searchBooks(keyword));
		model.addAttribute("keyword", keyword);
		return "list-books";
	}

	@RequestMapping(path="/book/{id}",method= RequestMethod.GET)
	public String findBookById(@PathVariable("id") Long id, Model model) {

		model.addAttribute(A_BOOK, bookService.findBookById(id));
		return "list-book";
	}

	@GetMapping("/add")
	public String showCreateForm(Book book, Model model) {

		model.addAttribute("categories", categoryService.findAllCategories());
		model.addAttribute("authors", authorService.findAllAuthors());
		model.addAttribute("publishers", publisherService.findAllPublishers());
		return "add-book";
	}

	@RequestMapping(path="/add-book",method= RequestMethod.POST)
	public String createBook(Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-book";
		}

		bookService.createBook(book);
		model.addAttribute(A_BOOK, bookService.findAllBooks());
		return REDIR_BOOKS;
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute(A_BOOK, bookService.findBookById(id));
		return "update-book";
	}

	@RequestMapping(path="/update-book/{id}",method= RequestMethod.POST)
	public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			return "update-book";
		}

		bookService.updateBook(book);
		model.addAttribute(A_BOOK, bookService.findAllBooks());
		return REDIR_BOOKS;
	}

	@RequestMapping(path="/remove-book/{id}",method= RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.deleteBook(id);

		model.addAttribute(A_BOOK, bookService.findAllBooks());
		return REDIR_BOOKS;
	}

}
