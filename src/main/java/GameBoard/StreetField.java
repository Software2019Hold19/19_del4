package GameBoard;
import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

import java.util.HashMap;

public class StreetField extends OwnableField {

    private String color;
    private GUIController gui;
    private String key; // use in hashmap

    HashMap<String, StreetField> pair = new HashMap<String, StreetField>();

    // only used for get rent and gets content from landonfield
    private GameBoard boardTemp;

    public StreetField(String name, String subName, String desc, String type, String rentStr, String color, String key){
        super(name, subName, desc, type, rentStr, key);

        /*
        String[] rentLst = rentStr.split(",");
        this.rent = new int[6];
        for (int i = 0; i < 6; i++){
            this.rent[i] = Integer.parseInt(rentLst[i]);
        }
*/
        this.color = color;
        this.key = key;
    }

    @Override
    public int getRent(GameBoard board) {
        // if the player owns all fields of same color and no houses 2x rent
        if (level == 0 && ownsSameColorFields(board))
        {
            return 2*rent[level];
        }
        else {
            return rent[level];
        }

    }

    public String getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String[] getInfo(){
        String[] info = new String[]{getName(), getSubName(), getDesc(), getType(), getPrice()+"", getColor(), getOwner(), getKey()};
        return info;
    }

    @Override
    public String toString(){
        return "[Name: " + getName() + ", SubName: " + getSubName() + ", Description: " + getDesc() + ", Type: " + getType() + ", Price: " + getPrice() + ", Color: " + getColor() + ", Owner: " + getOwner() + ", Key: " + getKey() + "]";
    }

    @Override
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib) throws InterruptedException {
        this.boardTemp = board;
        //Import LandOnField from ownablefield class
        super.landOnField(player, pLst, deck, board, gui, lib);

        if (this.owner.equals(player.getName())) {
            while (ownsSameColorFields(board) && chooseToBuyHouses(player, gui, lib)) {
                setHouseAndHotel(board);
                System.out.println("owns same color fields");
                gui.updateBoard(board.getOwnableBoard(), pLst);
                gui.updatePlayers(pLst);
            }
        }
        gui.updatePlayers(pLst);
    }
    
    // only to use when adding a house

    // set the house and hotels and the highest field in perspective to rent
    private void setHouseAndHotel(GameBoard board) {
        // get level of fields in same color
        // if they are all the same level add one to the higest price
        // else add one to the lowest level
        int[] listOfHouseLevel;

        if (this.color.equals("lightblue") || this.color.equals("brown")) {
            listOfHouseLevel = new int[2];
        }
        else {
            listOfHouseLevel = new int[3];
        }

        // get same color field level
        int fieldCnt = 0;
        for (OwnableField field : board.getOwnableBoard()) {
            if(field.getColor().equals(this.getColor())) {
               listOfHouseLevel[fieldCnt] = field.getHouseLevel();
               fieldCnt++;
            }
        }

        // chech if same level
        if (isFieldsSameLevel(listOfHouseLevel)) {
            board.getOwnableBoard()[getHighestPricFieldIndex(board.getOwnableBoard())].addOneLevel();
        }
        else { // add one to the lowest level
            board.getOwnableBoard()[getLowestLevelFieldIndex(board.getOwnableBoard())].addOneLevel();
        }
    }

    @Override
    public void sellHouseAndHotel(Player player, OwnableField[] playersFields) {
        int[] listOfHouseLevel;
        if (this.color.equals("lightblue") || this.color.equals("brown")) {
            listOfHouseLevel = new int[2];
        }
        else {
            listOfHouseLevel = new int[3];
        }

        int fieldCnt = 0;
        for (OwnableField field : playersFields) {
            if(field.getColor().equals(this.getColor())) {
               listOfHouseLevel[fieldCnt] = field.getHouseLevel();
               fieldCnt++;
            }
        }

        // chech if same level
        if (isFieldsSameLevel(listOfHouseLevel) && this.level == 0) {
            // sell field
            sellField(player);
        }
        else if (isFieldsSameLevel(listOfHouseLevel)) {
            playersFields[getHighestPricFieldIndex(playersFields)].minusOneLevel();
            player.addBal(getHousePrice());
        }
        else { // minus one to the highest level
            playersFields[getHighestLevelFieldIndex(playersFields)].minusOneLevel();
            player.addBal(getHousePrice());
        }
    }

    private int getHighestLevelFieldIndex(OwnableField[] fields) {
        int index = 100;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getColor().equals(this.color)){
                if (index == 100) {
                    index = i;
                }
                if (fields[i].getHouseLevel() >= fields[index].getHouseLevel()) {
                    index = i;
                }
            }
        }

        return index;
    }

    private int getLowestLevelFieldIndex(OwnableField[] fields) {
        int index = 100;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getColor().equals(this.color)){
                if (index == 100) {
                    index = i;
                }
                if (fields[i].getHouseLevel() <= fields[index].getHouseLevel()) {
                    index = i;
                }
            }
        }

        return index;
    }

    private int getHighestPricFieldIndex(OwnableField[] fields) {
        int index = 100;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getColor().equals(this.color)){
                if (index == 100) {
                    index = i;
                }
                if (fields[i].getPrice() >= fields[index].getPrice()) {
                    index = i;
                }
            }
        }

        return index;
    }

    private boolean isFieldsSameLevel(int[] list) {
        boolean sameLevel = true;
        for (int i = 1; i < list.length; i++) {
            if (sameLevel && list[i - 1] == list[i]) {
                sameLevel = true;
            }
            else {
                sameLevel = false;
            }
        }
        return sameLevel;
    }

/*
        super.landOnField(player, pLst, deck, board, gui, lib);
        if (this.owner.equals("")){
            gui.showMessage(String.format(lib.text.get("NotOwned"), price));
            this.owner = player.getName();
            player.addBal(-price);
            gui.updateBoard(board.getOwnableBoard(), pLst);
            //print - field not bought so you are buying this field
        }
        else if (player.getName().equals(this.owner)){ //player is the owner
            gui.showMessage(lib.text.get("OwnField"));
        }
        else{
            for(Player ownercheck : pLst){
                if(ownercheck.getName().equals(this.owner)){//with for loop it finds player who owns THIS field
                    int sameColorOwned = 0;//counter for how many fields of THIS color has THIS owner
                    for(OwnableField fieldCheck : board.getOwnableBoard()){//checks all fields (including this)
                        if(fieldCheck.getColor().equals(this.color) && fieldCheck.getOwner().equals(this.owner)){
                            sameColorOwned++;
                        }
                    }
                    if(ownercheck.getIsJailed()){  //Checks if the owner is in jailx
                        gui.showMessage(String.format(lib.text.get("OthersFieldJailed"), ownercheck.getName()));
                    }
                    else if(sameColorOwned == 2){
                        gui.showMessage(String.format(lib.text.get("OthersField"), ownercheck.getName(), 2*price));
                        player.addBal(-(2*this.getRent()));
                        ownercheck.addBal(2*this.getRent());
                    }
                    else{
                        gui.showMessage(String.format(lib.text.get("OthersField"), ownercheck.getName(), price));
                        player.addBal(-this.getRent());
                        ownercheck.addBal(this.getRent());
                    }
                }
            }
        }       
        
        // Log to console
        System.out.println(player.getName() + ": Landed on " + this.getName() + ", Field is owned by " + owner);

*/

    @Override
    public int getHousePrice() {
        if (this.price < 2000) {
            return 1000;
        }
        else if (this.price < 4000) {
            return 2000;
        }
        else if (this.price < 6000) {
            return 3000;
        }
        else {
            return 4000;
        }
    }

    private boolean ownsSameColorFields(GameBoard board){
        int ownSamecolorFieldCnt = 0;
        for (OwnableField field : board.getOwnableBoard()) {
            if(field.getColor().equals(this.getColor()) && field.getOwner().equals(this.owner)) {
                ownSamecolorFieldCnt++;
            }
        }
        //there are only 2 of lightblue and brown
        if((ownSamecolorFieldCnt == 2) && (this.color.equals("lightblue")  ||
                                            this.color.equals("brown"))) {
            return true;
        }
        //there are 3 of all other colors.
        else if((ownSamecolorFieldCnt == 3) && (this.color.equals("orange") ||
                                            this.color.equals("green")      ||
                                            this.color.equals("darkgrey")   ||
                                            this.color.equals("red")        ||
                                            this.color.equals("lightgrey")  ||
                                            this.color.equals("yellow"))) {
            return true;
        }
        else{
            return false;
        }

    }

    private boolean chooseToBuyHouses(Player player, GUIController gui, Translator lib) {

        // priser for huse <2000 1000kr. <4000 2000kr. <6000 3000kr.  ellers 4000kr.
        if (this.level <= 3) {
            int housePrice = getHousePrice();
            String choice = gui.getPlayerDropdown(String.format(lib.text.get("BuyHouse"), housePrice), lib.text.get("Yes"), lib.text.get("No"));

            if (choice.equals("Ja")) {
                if (player.getBal() > housePrice) {
                 //   this.level++;
                    player.addBal(-housePrice);
                    return true;
                }
                else {
                    gui.showMessage(lib.text.get("NoMoney"));
                    return false;
                }
            }
        }
        else if (this.level == 4) {
            int hotelPrice = getHousePrice();
            String choice = gui.getPlayerDropdown(String.format(lib.text.get("BuyHotel"), hotelPrice), lib.text.get("Yes"), lib.text.get("No"));

            if (choice.equals("Ja")) {
                if (player.getBal() > hotelPrice) {
                   // this.level++;
                    player.addBal(-hotelPrice);
                    return true;
                 }
                 else {
                     gui.showMessage(lib.text.get("NoMoney"));
                     return false;
                 }
            }
        }

        // never in use
        return false;
    }
}
