package sandbox.DotCom;

/**
 * Created by user on 13.05.2017.
 */
public class SimpleDotComTestDrive {
    public static void main(String[] args) {
        SimpleDotCom dot = new SimpleDotCom2();
        int[] location = {1,2,3};
        dot.setLocationCells(location);

        String userGuess = "4";
        System.out.print(userGuess + " : ");
        String result = dot.checkYourSelf(userGuess);

        for(int i=0;i<5;i++){
            System.out.print(Integer.toString(i) + " : ");
            dot.checkYourSelf(Integer.toString(i));
        }
    }
}
