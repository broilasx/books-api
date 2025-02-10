package com.example.books.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.domain.Book;
import com.example.books.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    
    private final BookService bookService;

    public BookController(final BookService bookService){
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(
        @PathVariable final String isbn, 
        @RequestBody final Book book){
            book.setIsbn(isbn);
            final Book savedBook = bookService.create(book);
            final ResponseEntity<Book> response = new ResponseEntity<Book>(book, HttpStatus.CREATED);
            return response;

    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
    }
}
