package com.example;

import com.example.entities.Author;
import com.example.entities.Book;
import com.example.entities.Publisher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookshopPU");
        EntityManager em = emf.createEntityManager();


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
        em.getTransaction().begin();
        em.persist(publisher);
        em.persist(pub1);
        em.persist(author);
        em.persist(book);
        em.persist(book1);
        em.getTransaction().commit();

        // em.getTransaction().begin();
        // em.persist(book1);
        // em.getTransaction().commit();
    }
}