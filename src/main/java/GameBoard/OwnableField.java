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
            payRent(player, pLst, board);
        }
        else if (this.owner.equals("")) {
            choiceToBuy(player, gui, lib, pLst);
        }

    }

    private void payRent(Player player, Player[] pLst, GameBoard board){
        Player tempPlayer = new Player("tmp");

        for (int i = 0; i < pLst.length; i++){
            if (getOwner().equals(pLst[i].getName())){
                tempPlayer = pLst[i];
            }
        }

        if(!tempPlayer.getIsJailed()){         //A player in jail can't collect rent
            player.addBal(-getRent(board));
            tempPlayer.addBal(getRent(board));
        }
    }

    @Override
    public int getRent(GameBoard board){return rent[level];}

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

    public int getHouseLevel() {
        return this.level;
    }

    public void choiceToBuy(Player player, GUIController gui, Translator lib, Player[] pLst){
        //shows dropdown with yes/no button to buy
        String buyChoice = gui.getPlayerDropbown(String.format(lib.text.get("ChoiceToBuy"), this.price), lib.text.get("Yes"), lib.text.get("No"));
        if(buyChoice.equals(lib.text.get("Yes"))) {
            // checking if the player has enough money
            if (player.getBal() >= this.price) {
                setOwner(player.getName());
                player.addBal(-this.price);
            }
            else {
                gui.showMessage(lib.text.get("NoMoney"));
            }
        }
        else {
            auction(player, pLst, gui, lib);

        }

    }

    public void auction(Player player, Player[] pLst, GUIController gui, Translator lib) {
        Player[] pInAuction = new Player[pLst.length - 2];
        int i = 0;
        for (Player p : pLst){
            if (!p.getName().equals(player.getName())){
                pInAuction[i++] = p;
            }
        }
        gui.showMessage(String.format(lib.text.get("AutionStart"), player.getName()));

        while (i > 2) {

        }

    }

    public Boolean auctionTurn(Player player, GUIController gui, Translator lib) {
        String Answer = gui.getPlayerBtn(String.format(lib.text.get("AuctionTurn"), player.getName()), lib.text.get("ActionYesBtn"), lib.text.get("ActionNoBtn"));
        if (Answer.equals(lib.text.get("ActionYesBtn"))) {
            int adding = 
        }
        return false;
    }

}