package com.knf.dev.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knf.dev.librarymanagementsystem.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
