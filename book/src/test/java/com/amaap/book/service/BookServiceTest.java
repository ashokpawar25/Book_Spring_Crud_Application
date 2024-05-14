package com.amaap.book.service;

import com.amaap.book.dao.BookRepository;
import com.amaap.book.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Test
    void shouldSaveBookIntoRepository() {
        // arrange
        Book expected = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookRepository.save(expected)).thenReturn(expected);

        // act
        Book actual = bookService.save(expected);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllBooksFromRepository() {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        List<Book> expected = List.of(book);
        when(bookRepository.findAll()).thenReturn(expected);

        // act
        List<Book> actual = bookService.getAllBooks();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetBookByNameFromRepository() {
        // arrange
        Book expected = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.of(expected));

        // act
        Book actual = bookService.getBook("Clean Code");

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetNullWhenBookWithNameIsNotFoundInRepository() {
        // arrange
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.empty());

        // act
        Book actual = bookService.getBook("Clean Code");

        // assert
        assertNull(actual);
    }

    @Test
    void shouldUpdateBookFromRepository() {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.of(book));

        // act
        boolean isUpdated = bookService.updateBook("Clean Code", book);

        // assert
        assertTrue(isUpdated);
    }

    @Test
    void shouldReturnNullWhenBookToUpdateIsNotFoundInRepository() {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.empty());

        // act
        boolean isUpdated = bookService.updateBook("Clean Code", book);

        // assert
        assertFalse(isUpdated);
    }

    @Test
    void shouldDeleteBookByNameFromRepository() {
        // arrange
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.of(new Book("Clean Code", "Uncle bob", "Oracle Press", 400)));

        // act
        boolean isDeleted = bookService.deleteBook("Clean Code");

        // assert
        assertTrue(isDeleted);
    }

    @Test
    void shouldReturnFalseWhenTryToDeleteBookWhichIsNotPresentInDatabase() {
        // arrange
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.empty());

        // act
        boolean isDeleted = bookService.deleteBook("Clean Code");

        // assert
        assertFalse(isDeleted);
    }
}