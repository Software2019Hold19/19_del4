package Main;

public class Dice {
    private Die die1 = new Die();
    private Die die2 = new Die();

    public Dice(){}

    public int[] roll(){
        int[] rolled = {die1.roll(), die2.roll()};
        return rolled;
    }

}
