package com.example.persistence;

import java.util.List;

import com.example.entities.Book;

public class BookDAO extends DAO<Book> {

    @Override
    public void save(Book book) {
        super.save(book);
    }

    public Book getBookById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be less than zero.");
        connect();
        Book book = em.find(Book.class, id);
        disconnect();
        return book;
    }

    public void deleteBookById(int id) {
        Book book = getBookById(id);
        super.delete(book);
    }

    public List<Book> getBooks() {
        connect();
        @SuppressWarnings("unchecked")
        //It's safe to assume that the result of the query can be casted to Book, because it came from the corresponding entity table.
        List<Book> books = em.createQuery("SELECT b FROM Book").getResultList();
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
