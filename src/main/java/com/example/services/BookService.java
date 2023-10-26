package com.example.services;

import java.util.List;

import com.example.entities.Author;
import com.example.entities.Book;
import com.example.entities.Publisher;
import com.example.persistence.BookDAO;

public class BookService {
    
    private final BookDAO DAO;


    public BookService() {
        DAO = new BookDAO();
    }

    public Book createBook(String title, int year, int totalStock, int rentedUnits, int availableUnits, Author author, Publisher publisher) {
        return new Book(title, year, totalStock, rentedUnits, availableUnits, author, publisher);
    }

    public void saveBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book cannot be null.");
        DAO.save(book);
    }

    public Book getBookByIsbn(long isbn) {
        return DAO.getBookByIsbn(isbn);
    }

    public boolean deleteBookByIsbn(long isbn) {
        try {
            DAO.deleteBookByIsbn(isbn);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Book> getBooks() {
        return DAO.getBooks();
    }

    public List<Book> getBooksByPublisher(String publisher) {
        return DAO.getBooksByPublisher(publisher);
    }

    public List<Book> getBooksByAuthor(String author) {
        return DAO.getBooksByAuthor(author);
    }

}
