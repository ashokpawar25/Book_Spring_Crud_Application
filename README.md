# Spring boot crud operations for book's API's

##### This is springboot application Designed to understand the crud operations for the book by TDD approch

## BookApplication class : The exection of program will start from this class

## controller package : In the controller package we define the controller classes , in which we design the API's

## BookController class : 
### Behaviours :
  -`public ResponseEntity<String> saveBook(@RequestBody Book book)` : This method is defined to create a new book
  -`public ResponseEntity<List<Book>> getAllBooks()` : This method is defined to get all books from the database
  -`public ResponseEntity<Book> getBook(@RequestParam(required = false) String name)` : This method is defined to get book by it's name
  -`public ResponseEntity<String> updateBook(@PathVariable("bookName") String bookName, @RequestBody Book book)` : This method will update the book if exist else return not found response
  -`public ResponseEntity<String> deleteBook(@PathVariable("bookName") String bookName)` : This method will delete the book if exist else return not found response

## service Package : In the service package we define the methods which will be called by the API's in the controller and will process the request

## BookService class :
### Behaviours :
  -`public Book save(Book book)` : This method check if book with same name already exist and save the book into database if it does not exist
  -`public List<Book> getAllBooks()` : This method will return all books present in the database
  -`public Book getBook(String bookName)` : This method returns a specific book 
  -`public boolean updateBook(String bookName, Book newBook)` : This method updates the book if it exist in the database
  -`public boolean deleteBook(String bookName)` : This method delete the book if it exist in the database

## dao package : In the dao package we define the repositories which can directly communicate on the database

## BookRepository : This is the repository defined for the bood enitity

## entity package : In this package we define the different entities which we use during application development.

## Book :
### States of Book :
  -`private String name`
  -`private String author`
  -`private String publication`
  -`private int price`
