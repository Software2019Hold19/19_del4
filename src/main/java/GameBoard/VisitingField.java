package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class VisitingField extends Field {

    public VisitingField(String name, String subName, String desc, String type){
        super(name, subName, desc, type);
    }

    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib) {
        if (this.getType().equals("jail")) {
            gui.showMessage(lib.text.get("LandOnJail"));
        } else {
            gui.showMessage(lib.text.get("LandOnFree"));
        }

        //just chillin
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
