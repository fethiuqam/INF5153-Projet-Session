package com.uqam.dao;
import com.uqam.model.Folder;
import com.uqam.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
                    .setParameter(1,username).setParameter(2,encryptPasswordToSHA1(password)).getSingleResult();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        return result;
    }

    private String encryptPasswordToSHA1 (String password){
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte [] arrayByte = md.digest();

            for (int i = 0; i < arrayByte.length; i++) {
                result.append(String.format("%02x", arrayByte[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public Folder findById(String id) {
        Folder result = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            result = entityManager.createQuery("FROM Folder f WHERE f.owner.insuranceNumber = ?1 ", Folder.class)
                    .setParameter(1,id).getSingleResult();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
        return result;
    }
}
