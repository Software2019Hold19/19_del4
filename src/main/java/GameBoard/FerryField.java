package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

/**
 * FerryField
 */
public class FerryField extends OwnableField{

    public FerryField(String name, String subName, String desc, String type, String rent, String key) {
        super(name, subName, desc, type, rent, key);
        this.key = key;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRent(GameBoard board){
        int ownedCount = 0;
        //check how many ferries this ferryowner got.
        for (OwnableField field : board.getOwnableBoard()) {
            if(this.owner.equals(field.getOwner()) && field.getType().equals("ferry")){
                ownedCount++;
            }
        }
        return this.rent[ownedCount-1]; //minus 1 to cuz index...
    }

}