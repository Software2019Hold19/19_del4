package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class    StartField extends Field {
    private int money = 4000;

    public StartField(String name, String subName, String desc, String type){
        super(name, subName, desc,type);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        gui.showMessage(lib.text.get("LandOnStart"));
        //money is added when passing this field (see Player.move())
        //metoden er her kun fordi alle fields skal have en
    }

    @Override
    public String[] getInfo(){
        String[] info = new String[]{getName(), getSubName(), getDesc(), getType(), getMoney()+""};
        return info;
    }

    @Override
    public String toString(){
        return "[Name: " + getName() + ", SubName: " + getSubName() + ", Description: " + getDesc() + ", Type: " + getType() + ", Money: " + getMoney() + "]";
    }
}
