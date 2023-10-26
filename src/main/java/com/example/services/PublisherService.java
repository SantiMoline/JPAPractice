package com.example.services;

import java.util.List;

import com.example.entities.Publisher;
import com.example.persistence.PublisherDAO;

public class PublisherService {
    
    private final PublisherDAO DAO;


    public PublisherService() {
        DAO = new PublisherDAO();
    }

    public Publisher createPublisher(String name) {
        return new Publisher(name);
    }

    public void savePublisher(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher cannot be null.");
        }
        DAO.save(publisher);
    }

    public Publisher getPublisherById(int id) {
        return DAO.getPublisherById(id);
    }

    public boolean deletePublisherById(int id) {
        try {
            DAO.deletePublisherById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Publisher> getPublishers() {
        try {
            return DAO.getPublishers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



}
