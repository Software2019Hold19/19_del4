package Main;

public class Dice {
    private Die die1 = new Die();
    private Die die2 = new Die();
    private int[][] testingRolls = {{1,6},
                                    {2,6},
                                    {2,2},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6},
                                    {1,6}};
    private int testIndex;

    public Dice(){}

    public int[] roll(Boolean testing){
        if (testing) {
            return testingRolls[testIndex++];
        }
        int[] rolled = {die1.roll(), die2.roll()};
        return rolled;
    }

}
