package com.knf.dev.librarymanagementsystem.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.exception.NotFoundException;
import com.knf.dev.librarymanagementsystem.repository.AuthorRepository;
import com.knf.dev.librarymanagementsystem.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Author> findAllAuthors() {
		return authorRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Author findAuthorById(Long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Author not found with ID %d", id)));
	}

	@Override
	public void createAuthor(Author author) {
		authorRepository.save(author);
	}

	@Override
	public void updateAuthor(Author author) {
		authorRepository.save(author);
	}

	@Override
	public void deleteAuthor(Long id) {
		var author = authorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Author not found with ID %d", id)));

		authorRepository.deleteById(author.getId());
	}

	@Override
	public Page<Author> findPaginated(Pageable pageable) {

		var pageSize = pageable.getPageSize();
		var currentPage = pageable.getPageNumber();
		var startItem = currentPage * pageSize;
		List<Author> list;

		if (findAllAuthors().size() < startItem) {
			list = Collections.emptyList();
		} else {
			var toIndex = Math.min(startItem + pageSize, findAllAuthors().size());
			list = findAllAuthors().subList(startItem, toIndex);
		}

		return new PageImpl<Author>(list, PageRequest.of(currentPage, pageSize), findAllAuthors().size());

	}

}
