package sandbox.DotCom;

import java.util.ArrayList;

/**
 * Created by user on 13.05.2017.
 */
public class DotComBust {
    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        DotCom one = new DotCom();
        one.setName("Pets.com");
        DotCom two = new DotCom();
        two.setName("eToys.com");
        DotCom three = new DotCom();
        three.setName("Go2.com");
        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        System.out.println("Ваша цель потопить 3 сайта");
        System.out.println("Pets.com, eToys.com, Go2.com");
        System.out.println("Попытайтесь потопить их за минимальное кол-во ходов");

        for (DotCom dotComToSet : dotComsList) {

            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.setLocationCells(newLocation);
        }
    }

    private void startPlaying() {
            while(!dotComsList.isEmpty()){

                String userGuess = helper.getUserInput("Сделай ход");
                checkUserGuess(userGuess);
            }
            finishGame();
    }

    private void checkUserGuess(String userGuess) {
            numOfGuesses++;
            String result = "Мимо";

            for(DotCom dotComToTest : dotComsList) {
                result = dotComToTest.checkYourSelf(userGuess);

                if(result.equals("Попал")) {
                    break;
                }
                if(result.equals("Потопил")) {
                    dotComsList.remove(dotComToTest);
                    break;
                }

            }
        System.out.println(result);

    }


    private void finishGame() {
        System.out.println("Все сайты ушли ко дну! Ваши акции теперь ничего не стоят!");
        if(numOfGuesses <=18) {
            System.out.println("Это заняло у вас всего "+ numOfGuesses + " попыток");
            System.out.println("Вы успели выбраться до того как все ваши вложения утонули");
        } else {
            System.out.println("Это заняло у вас довольно много времени. "+ numOfGuesses + " попыток");
            System.out.println("Рыбы водят хорооводы вокруг вашших вложений.");
        }
    }

    public static void main(String[] args) {
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }
}
