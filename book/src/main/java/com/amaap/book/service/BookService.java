package com.amaap.book.service;
import com.amaap.book.dao.BookRepository;
import com.amaap.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book save(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getName());
        if(existingBook.isPresent()) return null;
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String bookName) {
        Optional<Book> book = bookRepository.findById(bookName);
        return book.orElse(null);
    }

    public boolean updateBook(String bookName, Book newBook) {
        Optional<Book> book = bookRepository.findById(bookName);
        if(book.isPresent())
        {
            Book updatedBook = book.get();
            updatedBook.setAuthor(newBook.getAuthor());
            updatedBook.setPublication(newBook.getPublication());
            updatedBook.setPrice(newBook.getPrice());
            bookRepository.save(updatedBook);
            return true;
        }
        return false;
    }

    public boolean deleteBook(String bookName) {
        Optional<Book> book = bookRepository.findById(bookName);
        if(book.isPresent())
        {
            bookRepository.deleteById(bookName);
            return true;
        }else {
            return false;
        }
    }
}
