package com.uqam.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uqam.model.AppException;
import com.uqam.model.Folder;
import com.uqam.model.User;
import org.hibernate.exception.JDBCConnectionException;
import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

public class DataSource implements Searchable, Editable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @Override
    public User findByUsernameAndPassword(String username, String password) throws AppException {

        User result = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            result = entityManager.createQuery("FROM User u WHERE u.username = ?1 and u.password = ?2", User.class)
                    .setParameter(1, username).setParameter(2, encryptPasswordToSHA1(password)).getSingleResult();
        }catch (JDBCConnectionException e){
            throw new AppException("Erreur de connection avec la base de données");
        }catch (NoResultException e){
            throw new AppException("Auncun utilisateur trouvé. Veuillez vous assurer d'avoir mis le bon nom d'utilisateur et mot de passe.");
        } catch (Exception e) {
            throw new AppException("Erreur de base de données inconnu");
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
        return result;
    }

    private String encryptPasswordToSHA1(String password) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] arrayByte = md.digest();

            for (int i = 0; i < arrayByte.length; i++) {
                result.append(String.format("%02x", arrayByte[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public Folder findById(String id) throws AppException{
        Folder result = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            result = entityManager.createQuery("FROM Folder f WHERE f.owner.insuranceNumber = ?1 ", Folder.class)
                    .setParameter(1, id).getSingleResult();
        }catch (JDBCConnectionException e){
            throw new AppException("Erreur de connection avec la base de données");
        }catch (NoResultException e){
            throw new AppException("Aucun numero d'assurance maladie correspondant trouvé.");
        } catch (Exception e) {
            throw new AppException("Erreur de base de données inconnu");
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
        return result;
    }

    @Override
    public boolean update(Folder folder) throws AppException{
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

//            entityManager.unwrap(Session.class).saveOrUpdate(folder);
            entityManager.merge(folder);

            entityManager.getTransaction().commit();
        }catch (JDBCConnectionException e){
            throw new AppException("Erreur de connection avec la base de données");
        } catch (Exception e) {
            if (entityManager != null)
                entityManager.getTransaction().rollback();
            throw new AppException("Echec de la mise à jour du dossier sur la base de données");
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
        return true;
    }

    public boolean archiveModification(Folder folder) throws AppException{
        Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonFolder = json.toJson(folder);
        Date modificationDate = new Date(new java.util.Date().getTime());
        String insuranceNumber = folder.getOwner().getInsuranceNumber();
        Archive archive = new Archive(insuranceNumber, modificationDate, jsonFolder);
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(archive);
            entityManager.getTransaction().commit();
        }catch (JDBCConnectionException e){
            throw new AppException("Erreur de connection avec la base de données");
        } catch (Exception e) {
            if (entityManager != null)
                entityManager.getTransaction().rollback();
            throw new AppException("Echec de l'archivage du dossier sur la base de données");
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
        return true;
    }

}
