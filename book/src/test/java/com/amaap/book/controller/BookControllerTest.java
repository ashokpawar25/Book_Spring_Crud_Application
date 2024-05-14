package com.amaap.book.controller;

import com.amaap.book.entity.Book;
import com.amaap.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCreateABookInDatabase() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookService.save(any(Book.class))).thenReturn(book);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book Saved successfully"));
    }

    @Test
    void shouldReturnConflictMessageIfBookIsAlreadyPresentInDatabase() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookService.save(book)).thenReturn(null);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGetAllBooksFromDatabase() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Clean Code")))
                .andExpect(jsonPath("$[0].author", is("Uncle bob")))
                .andExpect(jsonPath("$[0].publication", is("Sample Publication")))
                .andExpect(jsonPath("$[0].price", is(250)));
    }

    @Test
    void shouldGetBookByNameFromDatabase() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookService.getBook("Clean Code")).thenReturn(book);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/book")
                                .param("name", "Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Clean Code")))
                .andExpect(jsonPath("$.author", is("Uncle bob")))
                .andExpect(jsonPath("$.publication", is("Sample Publication")))
                .andExpect(jsonPath("$.price", is(250)));
    }

    @Test
    void shouldGetNotFoundWhenBookNotFoundInDatabase() throws Exception {
        // arrange
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        when(bookService.getBook("Clean Code")).thenReturn(null);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/book")
                                .param("name", "Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateBookSuccessfully() throws Exception {
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);
        System.out.println("Mocked");
        when(bookService.updateBook(eq("Clean Code"), any(Book.class))).thenReturn(true);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/book/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book updated successfully"));
    }


    @Test
    void shouldReturnNotFoundWhenBookToUpdateIsNotFound() throws Exception {
        Book book = new Book("Clean Code", "Uncle bob", "Sample Publication", 250);

        when(bookService.updateBook("Clean Code", book)).thenReturn(false);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/book/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with this name is not found in database"));
    }


    @Test
    void shouldDeleteBookByNameFromDatabase() throws Exception {
        when(bookService.deleteBook("Clean Code")).thenReturn(true);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/book/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }

    @Test
    void shouldGetNotFoundResponseWhenTryToDeleteBookWhichIsNotPresentDatabase() throws Exception {
        when(bookService.deleteBook("Clean Code")).thenReturn(false);

        // act & assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/book/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
}