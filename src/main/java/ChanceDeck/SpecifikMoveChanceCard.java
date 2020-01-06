package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * SpecifikMoveChanceCard Move to Start or to "Strandpomenaden"
 */
public class SpecifikMoveChanceCard extends MoveChanceCard {

    boolean toStartJump = false;

    public SpecifikMoveChanceCard(String descriptionString, boolean toStart) {
        super(descriptionString);

        toStartJump = toStart;
    }

    @Override
    public boolean drawn(Player player, GameBoard board ) {
        if (toStartJump){
            player.blink(0);
            player.addBal(2);
            return false;
        }
        else {
            player.blink(23);
            return true;
        }
        
    }
}