package Main;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

    @Test
    public void testDiceRoll(){
        Dice dice = new Dice();
        int[] collector = new int[12];
        int testRolls = 10000000;
        int[] actual = new int[12];
        int[] expected = new int[]{0, 2, 5, 8, 11, 13, 16, 13, 11, 8, 5, 2};

        for(int i = 0; i < testRolls; i++){
            int[] roll = dice.roll(false);
            int val = roll[0] + roll[1];

            collector[val-1] += 1 ;
        }
        System.out.println("Expected\n" +
                "2: 2.778%\n" +
                "3: 5.556%\n" +
                "4: 8.333%\n" +
                "5: 11.111%\n" +
                "6: 13.889%\n" +
                "7: 16.667%\n" +
                "8: 13.889%\n" +
                "9: 11.111%\n" +
                "10: 8.333%\n" +
                "11: 5.556%\n" +
                "12: 2.778%\n");


        System.out.println("Actual");
        actual[0] = 0;

        for(int i = 1; i < collector.length; i++){
            float sum = (float) collector[i] / (float) testRolls;
            float percentage = sum * 100;

            actual[i]= (int) percentage;

            System.out.println((i+1) +": "+ percentage +"%");
        }

        assertArrayEquals(expected, actual);
    }

}