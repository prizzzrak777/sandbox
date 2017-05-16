package sandbox.DotCom;

/**
 * Created by user on 13.05.2017.
 */
public class SimpleDotComGame {
    public static void main(String[] args) {
        int numOfGuesses = 0;
        GameHelper helper = new GameHelper();
        SimpleDotCom theDotCom = new SimpleDotCom2();
        int randomNum = (int)(Math.random()*5);
        int[] location = {randomNum,++randomNum,++randomNum};
        theDotCom.setLocationCells(location);
        boolean isAlive = true;
        while(isAlive){
            String guess = helper.getUserInput("Введите число");
            String result = theDotCom.checkYourSelf(guess);
            numOfGuesses++;
            if(result.equals("Потопил")){
                isAlive=false;
                System.out.println("Вам потребовалось "+ numOfGuesses + " попыток(и)");
            }
        }
    }
}
