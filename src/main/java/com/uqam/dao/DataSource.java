package com.uqam.dao;
import com.uqam.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataSource implements Searchable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;


    @Override
    public User findByUsernameAndPassword(String username, String password) {

        User result = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            result = entityManager.createQuery("FROM User u WHERE u.username = ?1 and u.password = ?2", User.class)
                    .setParameter(1,username).setParameter(2,password).getSingleResult();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        return result;
    }
}
