package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

/**
 * OwnableField
 */
abstract class OwnableField extends Field {

    protected int price;
    
    protected String owner = " ";

    public OwnableField(String name, String subName, String desc, String type, String rentStr) {
        super(name, subName, desc, type);

        // Init price from subName
        this.price = Integer.parseInt(subName.split(" ")[0]);

        // Converting string to rent array
        String[] rentLst = rentStr.split(",");
        this.rent = new int[rentLst.length];
        for (int i = 0; i < rentLst.length; i++){
            this.rent[i] = Integer.parseInt(rentLst[i]);
        }
    }

    public void ownField () {

    }

    @Override
    public void landOnField (Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        super.landOnField(player, pLst, deck, board, gui, lib);

        if (!this.owner.equals(" ") && this.owner.equals(player.getName())){
            // payrent
        }
        else if (this.owner.equals(" ")) {
            // choise to buy
        }

    }

    private void payRent(Player player){

    }

    @Override
    public int getRent(){return rent[level];}

    public String getRentString() {
        return Integer.toString(rent[0]);
    }

    public String getColor() {
        return "";
    }

    public String getOwner() {
        return this.owner;
    }
    
    public int getPrice() {
        return this.price;
    }

}