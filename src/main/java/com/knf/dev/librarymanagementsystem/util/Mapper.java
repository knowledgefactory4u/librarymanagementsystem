package com.knf.dev.librarymanagementsystem.util;

import java.util.List;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.entity.Category;
import com.knf.dev.librarymanagementsystem.entity.Publisher;
import com.knf.dev.librarymanagementsystem.vo.AuthorRecord;
import com.knf.dev.librarymanagementsystem.vo.BookRecord;
import com.knf.dev.librarymanagementsystem.vo.CategoryRecord;
import com.knf.dev.librarymanagementsystem.vo.PublisherRecord;

public class Mapper {
	Mapper(){
		throw new IllegalStateException("Utility class");
	}

	public static List<BookRecord> bookModelToVo(List<Book> books) {
		return books.stream().map(vo ->
			new BookRecord(vo.getId(), vo.getIsbn(), vo.getName(), vo.getSerialName(),
					vo.getDescription())
		).toList();
	}

	public static List<AuthorRecord> authorModelToVo(List<Author> authors) {
		return authors.stream().map(vo ->
			new AuthorRecord(vo.getId(), vo.getName(), vo.getDescription())
		).toList();
	}

	public static List<CategoryRecord> categoryModelToVo(List<Category> categories) {
		return categories.stream().map(vo ->
				new CategoryRecord(vo.getId(), vo.getName())
		).toList();
	}

	public static List<PublisherRecord> publisherModelToVo(List<Publisher> publishers) {
		return publishers.stream().map(vo ->
			new PublisherRecord(vo.getId(), vo.getName())
		).toList();
	}

}
