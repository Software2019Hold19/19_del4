package Main;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

    @Test
    public void testDiceRoll(){
        Dice dice = new Dice();
        int[] collector = new int[12];
        int testRolls = 100000;

        for(int i = 0; i < testRolls; i++){
            int[] roll = dice.roll(false);
            int val = roll[0] + roll[1];

            collector[val-1] += 1 ;
        }

        for(int i = 1; i < collector.length; i++){
            float sum = (float) collector[i] / (float) testRolls;
            float percentage = sum * 100;

            System.out.println(i+1 +": "+ percentage +"%");
        }
    }

}