package com.example;

import com.example.entities.Author;
import com.example.entities.Book;
import com.example.entities.Publisher;
import com.example.persistence.AuthorDAO;
import com.example.persistence.BookDAO;
import com.example.persistence.PublisherDAO;


public class Main {
    public static void main(String[] args) {

        PublisherDAO pubDAO = new PublisherDAO();
        AuthorDAO authDAO = new AuthorDAO();
        BookDAO bookDAO = new BookDAO();

        Publisher publisher = new Publisher();
        publisher.setName("Sarasa");

        Publisher pub1 = new Publisher();
        pub1.setName("Salamanca");

        Author author = new Author();
        author.setName("J.K. Rowling");

        Book book = new Book();
        book.setAuthor(author);
        book.setPublisher(pub1);
        book.setAvailableUnits(500);
        book.setRentedUnits(15);
        book.setTotalStock(515);
        book.setTitle("Harry Potter");
        book.setYear(1998);
        
        Book book1 = new Book();
        book1.setAuthor(author);
        book1.setPublisher(pub1);
        book1.setAvailableUnits(500);
        book1.setRentedUnits(15);
        book1.setTotalStock(515);
        book1.setTitle("Lord of the rings");
        book1.setYear(1998);

        Book book2 = new Book();
        book2.setAuthor(author);
        book2.setPublisher(publisher);
        book2.setAvailableUnits(500);
        book2.setRentedUnits(15);
        book2.setTotalStock(515);
        book2.setTitle("Java Bootcamp");
        book2.setYear(1998);
        
        authDAO.save(author);
        pubDAO.save(pub1);
        pubDAO.save(publisher);
        bookDAO.save(book);
        bookDAO.save(book1);
        bookDAO.save(book2);

        bookDAO.getBooksByPublisher("Sarasa")
            .stream()
            .forEach(b -> System.out.println(b));
        bookDAO.getBooksByAuthor("J.K. Rowling")
            .stream()
            .forEach(b -> System.out.println(b));

    }
}