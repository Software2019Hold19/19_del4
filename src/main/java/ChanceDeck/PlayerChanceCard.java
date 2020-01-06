package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * PlayerChanceCard
 *  Buy field, if field is bought pay the owner
 */
public class PlayerChanceCard extends ChanceCard {

    int fieldInPlay;
    public PlayerChanceCard(String descriptionString, int field) {
        super(descriptionString);
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
     //   super.drawn(player, board);
        return false;
        // check if field is board and either buy or pay the owner
        
    }
}