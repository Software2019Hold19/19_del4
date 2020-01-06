package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class GoToJailField extends Field {

    public GoToJailField(String name, String subName, String desc, String type){
        super(name, subName, desc, type);
    }

    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        gui.showMessage(lib.text.get("LandOnGoToJail"));
        player.blink(6);
        player.addBal(-1);
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
