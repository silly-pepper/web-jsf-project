package model.dao;

import model.entity.*;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
@ManagedBean
@ApplicationScoped
public class UserAuthenticationDAO {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private UserAuth newUserAuth = new UserAuth();

    private void connectToDB() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("tableunit");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            System.out.println("Database successfully connected!");
        } catch (Exception e) {
            System.out.println("Error with database connection! " + e.getMessage());
        }
    }


    public void addNewUserAuth(){
        try {
            connectToDB();
            entityTransaction.begin();

//            newUserData.setUserDataId();
            newUserAuth.setLogin(newUserAuth.getLogin());
            newUserAuth.setPassword(newUserAuth.getPassword());



            entityManager.persist(newUserAuth);
            entityTransaction.commit();
            System.out.println("Added");
            entityManager.close();

        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error with database! " + e.getMessage());
        }
    }
    private void getUser() {
        try {
            connectToDB();
            entityTransaction.begin();
//            resultList = entityManager.createQuery("SELECT d FROM Result d", Result.class).getResultList();
            entityTransaction.commit();
            System.out.println("Data from database was successfully loaded!");
            entityManager.close();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error with loading data from database! " + e.getMessage());
        }
    }
    public UserAuth getNewUserAuth() {
        return newUserAuth;
    }
}
