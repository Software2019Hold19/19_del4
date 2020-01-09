package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * JailChanceCard
 * The player either go to prison or visits it
 */
public class JailChanceCard extends ChanceCard {

    
    public JailChanceCard(String descriptionString) {
        super(descriptionString);

    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
      //  super.drawn(player, board);
        player.blink(10);
        player.setIsJailed(true);
        // player hold card
        return false;
    }
}