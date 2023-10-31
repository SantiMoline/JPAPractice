package com.example.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Publisher;
import com.example.persistence.PublisherDAO;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;

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
        try {
            DAO.save(publisher);
        } catch (EntityExistsException e) {
            System.out.println("There is already an object stored with the same id.");
        } catch (SQLIntegrityConstraintViolationException | RollbackException e) {
            System.out.println("There is already a publisher with the same name registered in our DB.");
        }
    }

    public Publisher getPublisherById(int id) {
        return DAO.getPublisherById(id);
    }

    public Publisher getPublisherByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Publisher's name cannot be null or blank.");
        try {
            return DAO.getPublisherByName(name); 
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Sql exception.");
            System.out.println(e.getMessage());
            return null;
        }
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
