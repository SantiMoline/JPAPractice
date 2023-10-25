package com.example.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class DAO <T> {
    protected final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("bookshopPU");
    protected EntityManager em = EMF.createEntityManager();

    /**
     * Checks if there's an active connection with the database, and if not, starts one.
     */
    protected void connect() {
        if (!em.isOpen()) {
            em = EMF.createEntityManager();
        }
    }

    /**
     * Checks if there's an active connection with the database, and if exists, it gets disconnected.
     */
    protected void disconnect() {
        if(em.isOpen()) 
            em.close();
    }

    /**
     * Allows to persist and object into the DB. 
     * @param object Generic object to be stored in the DB.
     */
    protected void save(T object) {
        connect();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        disconnect();
    }

    /**
     * Allows to update the state of an existing object in the database. It must have the same value as a primary key.
     * @param object
     */
    protected void update(T object) {
        connect();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
        disconnect();
    }

    /**
     * Deletes the object recieved as a parameter from the database. It must have the same state as the one existing in the DB.
     * @param object
     */
    protected void delete(T object) {
        connect();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
        disconnect();
    }
    
}
