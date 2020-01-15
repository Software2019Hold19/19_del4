package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * MoveChanceCard
 * Based class for all cards that can move a player
 */
public class MoveChanceCard extends ChanceCard {

    public MoveChanceCard(String descriptionString) {
        super(descriptionString);
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
      //  super.drawn(player, board);

      return false;
    }    
}