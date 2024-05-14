package com.amaap.book.controller;

import com.amaap.book.entity.Book;
import com.amaap.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        if (savedBook == null) return new ResponseEntity<>("Book is already present in database", HttpStatus.CONFLICT);
        return ResponseEntity.ok("Book Saved successfully");
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/book")
    public ResponseEntity<Book> getBook(@RequestParam(required = false) String name, @RequestParam(required = false) String author) {
        Book book = bookService.getBook(name);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/{bookName}")
    public ResponseEntity<String> updateBook(@PathVariable("bookName") String bookName, @RequestBody Book book) {
        boolean isUpdated = bookService.updateBook(bookName, book);
        if (isUpdated) {
            return ResponseEntity.ok("Book updated successfully");
        }
        return new ResponseEntity<>("Book with this name is not found in database", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/book/{bookName}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookName") String bookName) {
        boolean isDeleted = bookService.deleteBook(bookName);
        if (isDeleted) {
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}
