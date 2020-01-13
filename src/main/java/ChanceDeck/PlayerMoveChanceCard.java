package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * PlayerMoveChanceCard
 * A Different player can move to a free field of their choice and buy it
 */
public class PlayerMoveChanceCard extends MoveChanceCard {

    public PlayerMoveChanceCard(String descriptionString) {
        super(descriptionString);
    }

    @Override
    public boolean drawn(Player player, GameBoard board){
        player.move(-3);

        return true;
    }
}