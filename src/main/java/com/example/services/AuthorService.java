package com.example.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Author;
import com.example.persistence.AuthorDAO;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;

public class AuthorService {
    
    private final AuthorDAO DAO;


    public AuthorService() {
        DAO = new AuthorDAO();
    }

    public Author createAuthor(String name) {
        return new Author(name);
    }

    public void saveAuthor(Author author) {
        if (author == null)
            throw new IllegalArgumentException("Author cannot be null.");
        if (getAuthorByName(author.getName()) != null) {
            System.out.println("There is already an author with that name in our DB.");
            return;
        }
        try {
            DAO.save(author);
        } catch (EntityExistsException e) {
            //System.out.println(e.getMessage());
            System.out.println("There is already an object stored with the same id.");
        } catch (SQLIntegrityConstraintViolationException | RollbackException e) {
            System.out.println("There is already an author with the same name registered in our DB.");
        }
    }

    public Author getAuthorById(int id) {
        return DAO.getAuthorById(id);
    }

    public Author getAuthorByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Author's name cannot be null or blank.");
        try {
            return DAO.getAuthorByName(name); 
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Sql exception.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deleteAuthorById(int id) {
        try {
            DAO.deleteAuthorById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Author> getAuthors() {
        try {
            return DAO.getAuthors();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
