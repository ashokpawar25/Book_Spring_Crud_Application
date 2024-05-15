# Spring boot crud operations for book's API's

This is springboot application Designed to understand the crud operations for the book by TDD approach.

## BookApplication class

The execution of program will start from this class.

## controller package

This package contains the controller classes responsible for handling incoming HTTP requests and defining the API endpoints for managing books.

## BookController class 

### Behaviours

- `public ResponseEntity<String> saveBook(@RequestBody Book book)`: This method is defined to create a new book.
- `public ResponseEntity<List<Book>> getAllBooks()`: This method is defined to get all books from the database.
- `public ResponseEntity<Book> getBook(@RequestParam(required = false) String name)`: This method is defined to get a book by its name.
- `public ResponseEntity<String> updateBook(@PathVariable("bookName") String bookName, @RequestBody Book book)`: This method will update the book if exist else return not found response.
- `public ResponseEntity<String> deleteBook(@PathVariable("bookName") String bookName)`: This method will delete the book if exist else return not found response.

## service Package

The service package classes handle requests from the controllers, perform necessary operations on the data, and interact with the data access layer.

## BookService class

### Behaviours

- `public Book save(Book book)`: This method checks if a book with the same name already exists and saves the book into the database if it does not exist.
- `public List<Book> getAllBooks()`: This method will return all books present in the database.
- `public Book getBook(String bookName)`: This method returns a specific book.
- `public boolean updateBook(String bookName, Book newBook)`: This method updates the book if it exists in the database.
- `public boolean deleteBook(String bookName)`: This method deletes the book if it exists in the database.

## dao package

The dao package includes the data access objects (DAOs) or repositories responsible for interacting with the database.

## BookRepository

This is the repository defined for the book entity.

## entity package

In this package, we define the different entities which we use during application development.

## Book 

### States of Book

- `private String name`
- `private String author`
- `private String publication`
- `private int price`
