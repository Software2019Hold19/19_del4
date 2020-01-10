package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

/**
 * TaxField
 */
public class TaxField extends Field {

    public TaxField(String name, String subName, String desc, String type, String key) {
        super(name, subName, desc, type);
        this.key = key;
        // TODO Auto-generated constructor stub
    }
    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        super.landOnField(player, pLst, deck, board, gui, lib);
        int payAmount = 0;
        if(this.key.equals("Tax1")){
            String choice = gui.getPlayerDropbown(lib.text.get("Tax1Choice"), "10%", "4000 kr.");
            if(choice.equals("10%")){
                payAmount = player.getBal() / 10;;
            }
            else if(choice.equals("4000 kr.")){
                payAmount = 4000;
            }
        }
        else if(this.key.equals("Tax2")){
            payAmount = 2000;
        }
        player.addBal(-payAmount);
    }
    
}