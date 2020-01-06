package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * BankChanceCard
 * either rechive or pay the bank money
 */
public class BankChanceCard extends ChanceCard {

    int changeBalanceBy = 0;

    public BankChanceCard(String descriptionString, int balanceChangeby) {
        super(descriptionString);
        changeBalanceBy = balanceChangeby;
    }

    public int getChangeBalanceBy() {
        return changeBalanceBy;
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
        super.drawn(player, board);

        // Change player object balance
        player.addBal(changeBalanceBy);
        return false;
    }
}