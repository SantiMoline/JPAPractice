package com.example.persistence;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Book;

public class BookDAO extends DAO<Book> {

    @Override
    public void save(Book book) throws SQLIntegrityConstraintViolationException {
        super.save(book);
    }

    public Book getBookByIsbn(long isbn) {
        if (isbn < 0)
            throw new IllegalArgumentException("Id cannot be less than zero.");
        connect();
        Book book = em.find(Book.class, isbn);
        disconnect();
        return book;
    }

    public List<Book> getBookByTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title cannot be null or blank");
        connect();
        @SuppressWarnings("unchecked")
        List <Book> books = em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title")
                    .setParameter("title", "%"+title+"%").getResultList();
        disconnect();
        return books;
    }

    public void deleteBookByIsbn(long isbn) {
        Book book = getBookByIsbn(isbn);
        super.delete(book);
    }

    public List<Book> getBooks() {
        connect();
        @SuppressWarnings("unchecked")
        //It's safe to assume that the result of the query can be casted to Book, because it came from the corresponding entity table.
        List<Book> books = em.createQuery("SELECT b FROM Book b").getResultList();
        disconnect();
        return books;
    }

    public List<Book> getBooksByPublisher(String publisher) {
        connect();
        @SuppressWarnings("unchecked")
        List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.publisher.name LIKE :pub")
            .setParameter("pub", publisher)
            .getResultList();
        disconnect();
        return books;
    }

    public List<Book> getBooksByAuthor(String author) {
        connect();
        @SuppressWarnings("unchecked")
        List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.author.name LIKE :auth")
            .setParameter("auth", author)
            .getResultList();
        disconnect();
        return books;
    }


    
}
