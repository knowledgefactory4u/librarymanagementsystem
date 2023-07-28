package com.knf.dev.librarymanagementsystem.controller;

import java.util.Optional;
import java.util.stream.IntStream;

import com.knf.dev.librarymanagementsystem.dto.AuthorDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.knf.dev.librarymanagementsystem.service.AuthorService;

@Controller
public class AuthorController {

	final AuthorService authorService;
	static final String AUTHOR_REDIRECT = "redirect:/authors";
	static final String UPDATE_AUTHOR = "update-author";
	static final String LIST_AUTHOR = "list-authors";
	static final String ADD_AUTHOR = "add-author";
	static final String AUTHOR = "author";
	static final String AUTHORS = "authors";

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping(path = "/authors")
	public String findAllAuthors(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);
		var bookPage = authorService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute(AUTHORS, bookPage);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return LIST_AUTHOR;
	}

	@GetMapping("/author/{id}")
	public String findAuthorById(@PathVariable("id") Long id, Model model) {

		model.addAttribute(AUTHOR, authorService.findAuthorById(id));
		return "list-author";
	}

	@GetMapping("/addAuthor")
	public String showCreateForm(@ModelAttribute("author") AuthorDTO author) {
		author.setName("Enter A Name");
		author.setDescription("Enter A Description");
		return ADD_AUTHOR;
	}

	@PostMapping("/add-author")
	public String createAuthor(AuthorDTO author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return ADD_AUTHOR;
		}
		authorService.createAuthor(author);
		model.addAttribute(AUTHOR, authorService.findAllAuthors());
		return AUTHOR_REDIRECT;
	}

	@GetMapping("/updateAuthor/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute(AUTHOR, authorService.findAuthorById(id));
		return UPDATE_AUTHOR;
	}

	@PostMapping("/update-author/{id}")
	public String updateAuthor(@PathVariable("id") Long id, AuthorDTO author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			author.setId(id);
			return UPDATE_AUTHOR;
		}

		authorService.updateAuthor(author);
		model.addAttribute(AUTHOR, authorService.findAllAuthors());
		return AUTHOR_REDIRECT;
	}

	@GetMapping("/remove-author/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, Model model) {
		authorService.deleteAuthor(id);

		model.addAttribute(AUTHOR, authorService.findAllAuthors());
		return AUTHOR_REDIRECT;
	}

}
