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
    private PublisherService ps;
    private AuthorService as;

    public BookService() {
        DAO = new BookDAO();
    }

    public void setServices(PublisherService ps, AuthorService as) {
        this.ps = ps;
        this.as = as;
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

    /**
     * Create a new instance of Book without assigning an Isbd (DB will handle that), and setting rented units = 0 and available units = total stock.
     * Recieves the Author and Publisher as String and generate the mandatory instances.
     * @param title
     * @param year
     * @param totalStock
     * @param authorsName
     * @param publishersName
     * @return
     */
    public Book createBook(String title, int year, int totalStock, String authorsName, String publishersName) {
        return new Book(title, year, totalStock, year, totalStock, new Author(authorsName), new Publisher(publishersName));
    }

    public void saveBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book cannot be null.");
        try {
            checkAuthor(book);
            checkPublisher(book);
            DAO.save(book);
        } catch (EntityExistsException e) {
            System.out.println("There is already an object stored with the same id.");
        } catch (SQLIntegrityConstraintViolationException | RollbackException e) {
            System.out.println("There is already a book with the same title registered in our DB.");
        }
    }

    /**
     * Validates if the book's author is already stored in the DB. In that case, sets the book's author field to the object Author retrieved from the DB.
     * @param book
     */
    private void checkAuthor(Book book) {
        Author auth = as.getAuthorByName(book.getAuthor().getName());
        if (auth == null) {
            auth = as.createAuthor(book.getAuthor().getName());
            as.saveAuthor(auth);
        }
        book.setAuthor(auth);
    }

    /**
     * Validates if the book's publisher is already stored in the DB. In that case, sets the book's publisher field to the object Author retrieved from the DB.
     * @param book
     */
    private void checkPublisher(Book book) {
        Publisher pub = ps.getPublisherByName(book.getPublisher().getName());
        if (pub == null) {
            pub = ps.createPublisher(book.getPublisher().getName());
            ps.savePublisher(pub);
        }
        book.setPublisher(pub);
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

    public List<Book> getBooksByTitle(String title) {
        try {
            return DAO.getBookByTitle(title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
