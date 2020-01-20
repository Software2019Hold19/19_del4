package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * SpecifikMoveChanceCard Move to Start or to "Strandpomenaden"
 */

public class SpecifikMoveChanceCard extends MoveChanceCard {

    int pos = 0;

    public SpecifikMoveChanceCard(String descriptionString, int pos) {
        super(descriptionString);
        this.pos = pos;
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
        if (pos > player.getFieldNumber()){
            int val = pos - player.getFieldNumber();
            player.move(val);


            return true;
        }
        else {
            int val = 40 - player.getFieldNumber() + pos;
            player.move(val);
            //player.addBal(4000);

            return true;
        }
        
    }
}