package Main;

public class Dice {
    private Die die = new Die();

    public Dice(){}

    public int roll(){
        return die.roll();
    }

}
