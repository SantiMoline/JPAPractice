package com.example.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libro", schema = "libreria")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;
    @Column(unique = true)
    private String title;
    private int year;
    private int totalStock;
    private int rentedUnits;
    private int availableUnits;
    private boolean active;

    @ManyToOne
    private Author author;
    @ManyToOne
    private Publisher publisher;
    
    public Book() {
        active = true;
    }

    public Book(String title, int year, int totalStock, int rentedUnits, int availableUnits, Author author, Publisher publisher) {
        setTitle(title);
        setYear(year);
        setTotalStock(totalStock);
        setRentedUnits(rentedUnits);
        setAvailableUnits(availableUnits);
        setActive(true);
        setAuthor(author);
        setPublisher(publisher);
    }

    public Book(Long isbn, String title, int year, int totalStock, int rentedUnits, int availableUnits, boolean active, Author author, Publisher publisher) {
        setIsbn(isbn);
        setTitle(title);
        setYear(year);
        setTotalStock(totalStock);
        setRentedUnits(rentedUnits);
        setAvailableUnits(availableUnits);
        setActive(active);
        setAuthor(author);
        setPublisher(publisher);
    }

    public Long getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalStock() {
        return this.totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getRentedUnits() {
        return this.rentedUnits;
    }

    public void setRentedUnits(int rentedUnits) {
        this.rentedUnits = rentedUnits;
    }

    public int getAvailableUnits() {
        return this.availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public String toString() {
        return
            "\nIsbn: " + getIsbn() +
            "\nTitle: " + getTitle() +
            "\nYear: " + getYear() +
            "\nTotal Stock: " + getTotalStock() +
            "\nRented Units: " + getRentedUnits() +
            "\nAvailable Units: " + getAvailableUnits() +
            "\nAuthor: " + getAuthor() +
            "\nPublisher: " + getPublisher();
    }


}
