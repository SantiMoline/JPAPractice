package com.example.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Author;
import com.example.entities.Book;
import com.example.entities.Publisher;
import com.example.persistence.BookDAO;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.RollbackException;

public class BookService {
    
    private final BookDAO DAO;


    public BookService() {
        DAO = new BookDAO();
    }

    /**
     * Create a new instance of Book without assigning an Isbd (DB will handle that), and setting rented units = 0 and available units = total stock.
     * @param title
     * @param year
     * @param totalStock
     * @param author
     * @param publisher
     * @return A new instance of Book with rented units = 0, Avaliable units = Total Stock.
     */
    public Book createBook(String title, int year, int totalStock, Author author, Publisher publisher) {
        return new Book(title, year, totalStock, 0, totalStock, author, publisher);
    }

    public void saveBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book cannot be null.");
        try {
            DAO.save(book);
        } catch (EntityExistsException e) {
            //System.out.println(e.getMessage());
            System.out.println("There is already an object stored with the same id.");
        } catch (SQLIntegrityConstraintViolationException | RollbackException e) {
            System.out.println("There is already a book with the same title registered in our DB.");
        }
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
        try {
            return DAO.getBooksByAuthor(author);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}
