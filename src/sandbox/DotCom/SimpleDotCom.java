package sandbox.DotCom;

/**
 * Created by user on 13.05.2017.
 */
public class SimpleDotCom
{
 int[] locationCells;
 int numOfHints = 0;

 public void setLocationCells(int[] locationCells){
     this.locationCells = locationCells;
 }

 public String checkYourSelf(String stringGuess){
     int guess = Integer.parseInt(stringGuess);
     String result = "Мимо";
     //for(int cell : locationCells){
     for(int i=0;i<locationCells.length;i++) {
         if(guess==locationCells[i]){
             result = "Попал";
             numOfHints++;
             locationCells[i]=-1;
             break;
         }
     }

     if(numOfHints == locationCells.length){
         result = "Потопил";
     }

     System.out.println(result);

     return  result;
 }
}
