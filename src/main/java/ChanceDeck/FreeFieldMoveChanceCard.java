package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

/**
 * FreeFieldMoveChanceCard
 * Move to a free field of a specific color, or 2, and either buy or pay the owner
 */
public class FreeFieldMoveChanceCard extends MoveChanceCard {

    int colorCount;
    Color fieldColors[];

    public FreeFieldMoveChanceCard(String descriptionString, Color[] colors) {
        super(descriptionString);

        colorCount = colors.length;
        fieldColors = new Color[colorCount];

        for (int i = 0; i < colorCount; i++) {
            fieldColors[i] = colors[i];
        }
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
      //  super.drawn(player, board);
        
        return false;

    }

    
}