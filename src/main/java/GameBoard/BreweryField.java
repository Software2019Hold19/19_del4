package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

/**
 * BrewerryField
 */
public class BreweryField extends OwnableField {

    public BreweryField(String name, String subName, String desc, String type, String rent, String key) {
        super(name, subName, desc, type, rent, key);
        this.key = key;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void landOnField (Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib) {
        guiHandler(gui, lib);//would run in Field (super.super.landonfield)
        //basically OwnableFields' landOnField method. Overriding this instead of getRent cuz needed parameter Player to get diceroll
        if (!this.owner.equals("") && !(this.owner.equals(player.getName()))) {
            //there is payment
            int ownedCount = 0;
            //check how many breweries the owner has
            for (OwnableField field : board.getOwnableBoard()) {
                if (this.owner.equals(field.getOwner()) && field.getType().equals("brewery")) {
                    ownedCount++;
                }
            }
            int payVal = this.rent[ownedCount - 1] * player.getLastRollVal();
            player.addBal(-payVal);
            //find owner and pay him
            for (Player ownerPlayer : pLst){
                if (ownerPlayer.getName().equals(this.owner)){
                    ownerPlayer.addBal(payVal);
                }
            }
        }
        else if (this.owner.equals("")) {
            //no owner no payment - can buy property
            super.choiceToBuy(player, gui, lib, pLst);
        }

    }

}