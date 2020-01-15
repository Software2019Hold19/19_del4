package Main;

public class Dice {
    private Die die1 = new Die();
    private Die die2 = new Die();
    private int[][] testingRolls = {{3,6},
                                    {1,1},
                                    {1,5},
                                    {1,5},
                                    {3,3},
                                    {1,1},
                                    {1,2},
                                    {3,1},
                                    {4,2},
                                    {1,1},
                                    {1,5},
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
        } else {
            int[] rolled = {die1.roll(), die2.roll()};
            return rolled;
        }
    }

}
