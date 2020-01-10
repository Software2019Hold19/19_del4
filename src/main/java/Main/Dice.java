package Main;

public class Dice {
    private Die die1 = new Die();
    private Die die2 = new Die();
    private int[][] testingRolls = {{3,6},
                                    {1,1},
                                    {1,5},
                                    {3,6},
                                    {1,6},
                                    {2,6},
                                    {2,2},
                                    {6,6},
                                    {1,6},
                                    {2,3},
                                    {1,3},
                                    {5,6},
                                    {3,6},
                                    {1,5},
                                    {3,6},
                                    {2,2},
                                    {1,2},
                                    {6,6},
                                    {1,4}};
    private int testIndex;

    public Dice(){}

    public int[] roll(Boolean testing){
        if (testIndex >= testingRolls.length){
            testIndex = 0;
        }
        if (testing) {
            return testingRolls[testIndex++];
        }
        int[] rolled = {die1.roll(), die2.roll()};
        return rolled;
    }

}
