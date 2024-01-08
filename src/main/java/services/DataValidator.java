package services;

public class DataValidator {

    public boolean checkNull(double x, double y, double r){
        if (String.valueOf(x) != null && String.valueOf(y) != null && String.valueOf(r) != null){
            return true;
        }
        return false;
    }


}
