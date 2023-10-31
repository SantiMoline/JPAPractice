package com.example.persistence;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Author;

public class AuthorDAO extends DAO<Author> {
    
    @Override
    public void save(Author auth) throws SQLIntegrityConstraintViolationException {
        super.save(auth);
    }

    public Author getAuthorById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be less than zero.");
        connect();
        Author author = em.find(Author.class, id);
        disconnect();
        return author;
    }

    /**
     * Search in the DB authors containing the indicated name. Returns a list containing all the possible objects.
     * @param name
     * @return
     */
    public Author getAuthorByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank.");
        connect();
        Author auth = (Author) em.createQuery("SELECT a FROM Author a WHERE a.name LIKE :name")
                        .setParameter("name", name).getSingleResult();
        disconnect();
        return auth;
    }

    public void deleteAuthorById(int id) {
        Author author = getAuthorById(id);
        super.delete(author);
    }

    public List<Author> getAuthors() {
        connect();
        @SuppressWarnings("unchecked")
        //It's safe to assume that the result of the query can be casted to Author, because it came from the corresponding entity table.
        List<Author> authors = em.createQuery("SELECT a FROM Author a").getResultList();
        disconnect();
        return authors;
    }
}
