package ChanceDeck;

import GUI.GUIController;
import GameBoard.GameBoard;
import Main.Player;
import Main.Translator;

/**
 * ChanceCard
 *  Base class for all chance cards in the game
 */
public class ChanceCard {

    protected boolean isInUse = false;
    String description = "";

    public ChanceCard(String descriptionString) {
        description = descriptionString;
    }

    public boolean drawn(Player player, GameBoard board ) {
        return false;
    }

    public void guiHandler(GUIController gui, Translator lib){
        gui.showMessage(description);
    }


    public boolean getIsInUse() {
        return isInUse;
    }

    public void setIsInUse(boolean inUse) {
        isInUse = inUse;
    }
    
}