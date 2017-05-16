package sandbox.DotCom;

import java.util.ArrayList;

/**
 * Created by user on 13.05.2017.
 */
public class DotCom {
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList<String> locationCells) {
        this.locationCells = locationCells;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String checkYourSelf(String stringGuess) {
        String result = "Мимо";
        int index = locationCells.indexOf(stringGuess);
        if (index>=0) {
            locationCells.remove(index);
            if (locationCells.isEmpty()) {
                result = "Потопил";
            } else {
                result = "Попал";
            }
        }
        return  result;
    }
}
