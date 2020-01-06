package Main;

import java.util.Random;

public class    Die {
    private int sides = 6;
    private int faceValue;

    public Die(){     //constructor
    }

    public int roll(){
        Random random = new Random();
        faceValue = random.nextInt(sides) + 1;
        return faceValue;
    }
}
