package com.knf.dev.librarymanagementsystem.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.service.AuthorService;

@Controller
public class AuthorController {

	final AuthorService authorService;
	final static String aAUTHORREDIRECT = "redirect:/authors";
	final static String aUPDATEAUTHOR = "update-author";
	final static String aLISTAUTHOR = "list-authors";
	final static String aADDAUTHOR = "add-author";
	final static String aAUTHOR = "author";
	final static String aAUTHORS = "authors";

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@RequestMapping("/authors")
	public String findAllAuthors(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);
		var bookPage = authorService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute(aAUTHORS, bookPage);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return aLISTAUTHOR;
	}

	@RequestMapping("/author/{id}")
	public String findAuthorById(@PathVariable("id") Long id, Model model) {

		model.addAttribute(aAUTHOR, authorService.findAuthorById(id));
		return "list-author";
	}

	@GetMapping("/addAuthor")
	public String showCreateForm(Author author) {
		return aADDAUTHOR;
	}

	@RequestMapping("/add-author")
	public String createAuthor(Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return aADDAUTHOR;
		}

		authorService.createAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		return aAUTHORREDIRECT;
	}

	@GetMapping("/updateAuthor/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute(aAUTHOR, authorService.findAuthorById(id));
		return aUPDATEAUTHOR;
	}

	@RequestMapping("/update-author/{id}")
	public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			author.setId(id);
			return aUPDATEAUTHOR;
		}

		authorService.updateAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		return aAUTHORREDIRECT;
	}

	@RequestMapping("/remove-author/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, Model model) {
		authorService.deleteAuthor(id);

		model.addAttribute("author", authorService.findAllAuthors());
		return aAUTHORREDIRECT;
	}

}
