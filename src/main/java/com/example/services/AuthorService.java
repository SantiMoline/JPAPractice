package com.example.services;

import java.util.List;

import com.example.entities.Author;
import com.example.persistence.AuthorDAO;

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
        DAO.save(author);
    }

    public Author getAuthorById(int id) {
        return DAO.getAuthorById(id);
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
