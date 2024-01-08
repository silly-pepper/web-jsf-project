package model.dao;

import beans.*;
import services.HitValidator;
import model.entity.Result;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.io.IOException;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class HitResult {
    private List<Result> resultList;
    private Result newResult;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private final HitValidator validator = new HitValidator();


    public HitResult() throws IOException {
        connectToDB();
        loadDB();
        newResult = new Result();
    }

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

    private void loadDB() {
        try {
            connectToDB();
            entityTransaction.begin();
            resultList = entityManager.createQuery("SELECT d FROM Result d", Result.class).getResultList();
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


    public void addResults() {
        try {
            connectToDB();
            entityTransaction.begin();
            long startTime = System.nanoTime();
            double time = (System.nanoTime() - startTime);
            if ((validator.checkNull(newResult.getX(), newResult.getY(), newResult.getR())) &&
                    validator.checkRange(newResult.getX(), newResult.getY(), newResult.getR())) {
                newResult.setResult(validator.checkResult(newResult.getX(), newResult.getY(), newResult.getR()));
                TimeBean timeBean = new TimeBean();
                newResult.setCurrentTime(timeBean.learnTime(startTime));
                newResult.setTime(time);
                resultList.add(newResult);
                System.out.println(newResult.toString());
                entityManager.persist(newResult);
                entityTransaction.commit();
                System.out.println("Added");
                entityManager.close();
                newResult = new Result();
            } else {
                System.out.println("Error with data validation!");
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error with database! " + e.getMessage());
        }
    }

    public void clearResults() {
        try {
            connectToDB();
            entityTransaction.begin();
            entityManager.createQuery("DELETE FROM Result", Result.class).executeUpdate();
            resultList.clear();
            entityTransaction.commit();
            System.out.println("Database was successfully cleared!");
            entityManager.close();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.out.println("Error with database cleanup!" + e.getMessage());

        }
    }

    public Result getNewResult() {
        return newResult;
    }
    public List<Result> getResultList(){

        return resultList;
    }
    public void setResultList(List<Result> resultList){
        this.resultList = resultList;
    }
    public void setNewResult(Result newResult){
        this.newResult = newResult;
    }
}

