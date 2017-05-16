package sandbox.DotCom;

import java.util.ArrayList;

/**
 * Created by user on 13.05.2017.
 */

public class SimpleDotCom2 extends SimpleDotCom {
    private ArrayList<String> locationCells;

    public void setLocationCells(ArrayList<String> locationCells) {
        this.locationCells = locationCells;
    }

    @Override
    public void setLocationCells(int[] locationCells) {
        this.locationCells = new ArrayList<String>();
        for(int cell : locationCells){
           this.locationCells.add(Integer.toString(cell));
        }

    }

    @Override
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

        System.out.println(result);

        return  result;
    }
}
