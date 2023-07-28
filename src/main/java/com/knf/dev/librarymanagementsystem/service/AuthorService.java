package com.knf.dev.librarymanagementsystem.service;

import java.util.List;

import com.knf.dev.librarymanagementsystem.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.knf.dev.librarymanagementsystem.entity.Author;

public interface AuthorService {

	public List<Author> findAllAuthors();

	public Author findAuthorById(Long id);

	public void updateAuthor(Author author);

	void updateAuthor(AuthorDTO author);

	public void deleteAuthor(Long id);

	public Page<Author> findPaginated(Pageable pageable);

	void createAuthor(AuthorDTO author);
}
