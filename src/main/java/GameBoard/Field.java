package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public abstract class Field {
    private String name;
    private String type; //"brewery", "ferry", "start", "street", "chance" "jail" or "visit"
    private String subName;
    private String desc;
    protected int rent[];
    protected int level;

    public Field(String name, String subName, String desc, String type){
        this.name = name;
        this.type = type;
        this.subName = subName;
        this.desc = desc;
        this.level = 0;
    }

    public String toString() {return "";}

    public int getRent() {
        return rent[0];
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getSubName() {
        return this.subName;
    }

    public String getDesc() {
        return this.desc;
    }

    public String[] getInfo(){

        return null;
    }

    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        guiHandler(gui, lib);
    }

    public void guiHandler(GUIController gui, Translator lib){
        gui.showMessage(getDesc());
    }
}
