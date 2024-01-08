package view;

import model.dao.HitResult;
import model.entity.Result;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class ResultTableView {
    public Result newResult;
    private  HitResult hitResultDAO = new HitResult();



    public ResultTableView() throws IOException {
    }

    public List<Result> getHitResultList() {
        List<Result> hitResultsFromDB = new ArrayList<>();

        try {
            hitResultsFromDB = hitResultDAO.getResultList();
        } catch (Exception e) {
            System.out.println("Cannot get all results from the database");
            FacesContext.getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "/error.xhtml");
        }

        return hitResultsFromDB;
    }

    public void clearTable() {

        try {

            hitResultDAO.clearResults();
            System.out.println( "Cleared hit results successfully.");
        } catch (Exception e) {
            System.out.println("Cannot clear results");

        }
    }

    public void submitResults(){
        try {

            hitResultDAO.addResults();
        } catch(Exception e){
            System.out.println("cannot add new result");

        }
    }
    public Result getNewResult() {
        return hitResultDAO.getNewResult();
    }





}
