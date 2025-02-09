package com.example.books.controllers;

import static com.example.books.TestData.testBook;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.books.TestData;
import com.example.books.domain.Book;
import com.example.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testeThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content("application/json"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatRetrieveBookReturns484WhenBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/123123123"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturnsHttp200AndBookWhenExists() throws Exception {
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getIsbn()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatListBooksReturnsHttp200EmptyListWhenNoBooksExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatListBooksReturnsHttp200AndBooksWhenBooksExist() throws Exception {
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));
    }
}
