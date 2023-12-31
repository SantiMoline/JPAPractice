package com.example.persistence;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.entities.Publisher;

public class PublisherDAO extends DAO<Publisher> {
    
    @Override
    public void save (Publisher pub) throws SQLIntegrityConstraintViolationException {
        super.save(pub);
    }

    public Publisher getPublisherById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be less than zero.");
        }
        connect();
        //Publisher publisher = (Publisher) em.createQuery("SELECT p FROM Publisher p WHERE p.id = :id").setParameter("id", id).getSingleResult();
        Publisher pub = em.find(Publisher.class, id);
        disconnect();
        return pub;
    }

    public void deletePublisherById(int id) {
        // connect(); not needed because the method getPublisherById() already initiate the connection.
        Publisher publisher = getPublisherById(id);
        super.delete(publisher);
    }

    public Publisher getPublisherByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Publisher's name cannot be null or blank.");
        connect();
        Publisher publishers = (Publisher) em.createQuery("SELECT p FROM Publisher p WHERE p.name LIKE :name")
                                    .setParameter("name", name).getSingleResult();
        disconnect();
        return publishers;
    }

    public List<Publisher> getPublishers() {
        connect();
        @SuppressWarnings("unchecked")
        //It's safe to assume that the result of the query can be casted to Publisher, because it came from the corresponding entity table.
        List<Publisher> publishers = em.createQuery("SELECT p FROM Publisher p").getResultList();
        disconnect();
        return publishers;
    }


}
