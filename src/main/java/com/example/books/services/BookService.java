package com.example.books.services;

import java.util.Optional;

import com.example.books.domain.Book;

public interface BookService {

    Book create(Book book);

    Optional<Book> findById(String isbn);
}
