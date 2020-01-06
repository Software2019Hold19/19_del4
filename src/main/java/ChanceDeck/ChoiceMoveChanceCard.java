package ChanceDeck;

import GUI.GUIController;
import GameBoard.GameBoard;
import Main.Player;
import Main.Translator;

/**
 * ChoiceMoveChanceCard
 * (Move up to 5 fields) new car (1 field or take new chancecard)
 */
public class ChoiceMoveChanceCard extends MoveChanceCard {

    boolean moveUpToFiveFields;
    String moveFields = "-1";

    public ChoiceMoveChanceCard(String descriptionString, boolean moveFivefieldsCard) {
        super(descriptionString);
        moveUpToFiveFields = moveFivefieldsCard;
    }

    @Override
    public boolean drawn(Player player, GameBoard board) {
      //  super.drawn(player, board);

        // Move (1-5 field) or card if 0 draw new card
        if (moveUpToFiveFields) {
            // Chose number of fields player want to move
            player.move(Integer.parseInt(moveFields));
            return true;
        }
        else {

            // choose to draw new card or move 1 field
            if (Integer.parseInt(moveFields) == 1) {
                player.move(1);
                return true;
            }

            else {
                //draw new card
                return true;
            }
        }
    }

    @Override
    public void guiHandler(GUIController gui, Translator lib){
        super.guiHandler(gui, lib);
        
        if (moveUpToFiveFields)
        {

            moveFields = gui.getPlayerDropbown(lib.text.get("ChanceCTxt3"), "1", "2", "3", "4", "5");
            
        }
        else
        {
            // move 1 fields or draw new card
            moveFields = gui.getPlayerDropbown(lib.text.get("ChanceCTxt4"), "0", "1");
            
        }

    }

    
}