package GameBoard;

import ChanceDeck.ChanceDeck;
import ChanceDeck.ChanceCard;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class ChanceField extends Field {

    ChanceCard card;

    public ChanceField(String name, String subName, String desc, String type){
        super(name, subName, desc, type);
    }

    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
       // super.landOnField(player, pLst, deck, board, gui, lib);

        System.out.println(getDesc());

        card = deck.draw();
        guiHandler(gui, lib);

        // return true if player moves;
        if (card.drawn(player, board)){
            gui.updatePlayers(pLst);
            board.getBoard()[player.getFieldNumber()].landOnField(player, pLst, deck, board, gui, lib);
        }


        System.out.println("Player new balance: " + player.getBal());
        
    }

    @Override
    public void guiHandler(GUIController gui, Translator lib) {
        super.guiHandler(gui, lib);

        card.guiHandler(gui, lib);
    }

    @Override
    public String[] getInfo() {
        String[] info = new String[]{getName(), getSubName(), getDesc(), getType()};
        return info;
    }

    @Override
    public String toString(){
        return "[Name: " + getName() + ", SubName: " + getSubName() + ", Description: " + getDesc() + ", Type: " + getType() + "]";
    }
}
