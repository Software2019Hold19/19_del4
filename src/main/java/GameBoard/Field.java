package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public abstract class Field {
    private String name;
    private String type; // "start", "street", "chance" "jail" or "visit"
    private String subName;
    private String desc;

    public Field(String name, String subName, String desc, String type){
        this.name = name;
        this.type = type;
        this.subName = subName;
        this.desc = desc;
    }

    public String toString() {return "";}

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSubName() {
        return subName;
    }

    public String getDesc() {
        return this.desc;
    }

    public String[] getInfo(){

        return null;
    }

    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib, Boolean testing){
        guiHandler(gui, lib);
    }

    public void guiHandler(GUIController gui, Translator lib){
        gui.showMessage(getDesc());
    }
    public String getColor(){return "";}
    public String getOwner(){return "";}
}
