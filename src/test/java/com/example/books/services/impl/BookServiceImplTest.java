package com.example.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.books.domain.Book;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = Book.builder()
            .isbn("02345678")
            .author("Virginia Woolf")
            .title("The Waves")
            .build();

            final BookEntity bookEntity = BookEntity.builder()
            .isbn("02345678")
            .author("Virginia Woolf")
            .title("The Waves")
            .build();

            when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

            final Book result = underTest.create(book);
            assertEquals(book, result);
    }
}
