package GameBoard;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

import ChanceDeck.ChanceDeck;

/**
 * OwnableField
 */
public abstract class OwnableField extends Field {

    protected int price;
    protected String owner = "";

    public OwnableField(String name, String subName, String desc, String type, String rentStr, String key) {
        super(name, subName, desc, type, key);

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

        if (!this.owner.equals("") && !(this.owner.equals(player.getName()))){
            payRent(player, pLst, board, lib, gui);
        }
        else if (this.owner.equals("")) {
            choiceToBuy(player,gui,lib);
        }
        else if (this.owner.equals(player.getName())){
            gui.showMessage(lib.text.get("OwnField"));
        }

    }

    private void payRent(Player player, Player[] pLst, GameBoard board, Translator lib, GUIController gui){
        Player tempPlayer = new Player("tmp");

        for (int i = 0; i < pLst.length; i++){      //finds owners player object
            if (getOwner().equals(pLst[i].getName())){
                tempPlayer = pLst[i];
            }
        }

        if(!tempPlayer.getIsJailed()){         //A player in jail can't collect rent

            if(this.type.equals("brewery")){ //brewery needs "player" object to calculate rent so uses different "getRent"method
                gui.showMessage(String.format(lib.text.get("OthersField"), this.owner, getRent(board, player)));
                player.addBal(-getRent(board, player));
                tempPlayer.addBal(getRent(board, player));
            }
            else {//all other ownable fields
                gui.showMessage(String.format(lib.text.get("OthersField"), this.owner, getRent(board)));
                player.addBal(-getRent(board));
                tempPlayer.addBal(getRent(board));
            }
        }
        else {          //if player is jailed
            gui.showMessage(String.format(lib.text.get("OthersFieldJailed"), tempPlayer.getName()));
        }
    }

    @Override
    public int getRent(GameBoard board){return rent[level];}

    public int getRent(GameBoard Board, Player player){//lavet for at kunne bruge player i brewery!
        return rent[level];
    }

    public String getRentString() {
        return Integer.toString(rent[0]);
    }

    public String getColor() {
        return "";
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String name) {
        this.owner = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void addOneLevel() {
        this.level++;        
    }

    public void choiceToBuy(Player player, GUIController gui, Translator lib){
        //shows dropdown with yes/no button to buy
        String buyChoice = gui.getPlayerDropbown(String.format(lib.text.get("ChoiceToBuy"), this.price), lib.text.get("Yes"), lib.text.get("No"));
        if(buyChoice.equals(lib.text.get("Yes"))){
            setOwner(player.getName());
            player.addBal(-this.price);
        }
    }
}