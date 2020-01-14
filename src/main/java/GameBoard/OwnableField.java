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
    protected boolean mortage;
    protected int auctionPrice;

    public OwnableField(String name, String subName, String desc, String type, String rentStr, String key) {
        super(name, subName, desc, type, key);

        // Init price from subName
        this.price = Integer.parseInt(subName.split(" ")[0]);
        this.auctionPrice = price / 2;

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
    public void landOnField (Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib) throws InterruptedException {
        super.landOnField(player, pLst, deck, board, gui, lib);


        if(getMortage()){
            gui.showMessage(lib.text.get("IsMortgage"));
        }
        else if (!this.owner.equals("") && !(this.owner.equals(player.getName()))){
            payRent(player, pLst, board, lib, gui);
        }
        else if (this.owner.equals("")) {
            choiceToBuy(player, gui, lib, pLst);
        }
        else if (this.owner.equals(player.getName())){
            gui.showMessage(lib.text.get("OwnField"));
        }

        gui.updatePlayers(pLst);
    }


    private void payRent(Player player, Player[] pLst, GameBoard board, Translator lib, GUIController gui){
        Player tempPlayer = new Player("tmp", 0);

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

    public void setMortage(boolean set){
        mortage = set;
    }

    public boolean getMortage(){
        return mortage;
    }

    protected void sellField(Player player) {
        this.owner = "";
        player.addBal(this.price);
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

    public void minusOneLevel() {
        if (this.level > 0)
            this.level--;
    }

    public int getHouseLevel() {
        return this.level;
    }


    public int getHousePrice() {
        return 0;
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
        Player[] pInAuction = new Player[pLst.length - 1];
        int i = 0;
        for (Player p : pLst){
            if (!p.getName().equals(player.getName()) && p.getAlive()){
                pInAuction[i++] = p;
            }
        }
        gui.showMessage(String.format(lib.text.get("AuctionStart"), player.getName()));
        int j = 0;
        while (pInAuction.length > 1) {
            Player currentP = pInAuction[j++];
            if (auctionTurn(currentP, gui, lib, this.auctionPrice)){
                gui.showMessage(String.format(lib.text.get("AuctionNext"), currentP.getName()));
            } else {
                gui.showMessage(String.format(lib.text.get("AuctionOut"), currentP.getName()));
                j--;
                Player[] tmp = new Player[pInAuction.length - 1];
                int l = 0;
                for (int k = 0; k < pInAuction.length; k++){
                    if (!pInAuction[k].getName().equals(currentP.getName())){
                        tmp[l++] = pInAuction[k];
                    }
                }
                pInAuction = tmp;
            }
            if (j >= pInAuction.length) {
                j = 0;
            }
        }
        gui.showMessage(String.format(lib.text.get("AuctionWin"), pInAuction[0].getName(), this.auctionPrice));
        setOwner(pInAuction[0].getName());
        pInAuction[0].addBal(-this.auctionPrice);
        this.auctionPrice = price / 2;

    }

    public void sellHouseAndHotel(Player player, OwnableField[] playersFields){
        sellField(player);
    }


    public Boolean auctionTurn(Player player, GUIController gui, Translator lib, int price) {
        if (price < player.getBal()) {
            String Answer = gui.getPlayerBtn(String.format(lib.text.get("AuctionTurn"), price, player.getName()), lib.text.get("AuctionYesBtn"), lib.text.get("AuctionNoBtn"));
            if (Answer.equals(lib.text.get("AuctionYesBtn"))) {
                int adding = gui.getPlayerInt(String.format(lib.text.get("AuctionAddMoney"), price, player.getName()), price, player.getBal());
                this.auctionPrice = price + adding;
                return true;
            } else {
                return false;
            }
        } else {
            gui.showMessage(String.format(lib.text.get("AuctionNoMoney"), player.getName()));
            return false;
        }

    }

}