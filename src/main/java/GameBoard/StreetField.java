package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class StreetField extends OwnableField {

    
    private int houseLevel = 0;
    private String color;

    private String key; // use in hashmap

    public StreetField(String name, String subName, String desc, String type, int[] price, String color, String key){
        super(name, subName, desc, type);

        this.rent = new int[6];
        this.rent = price;

        this.color = color;
        this.key = key;
    }

    public int getRent() {
        if (houseLevel != 0) {
            return rent[houseLevel];
        }
        else { // TODO: return 2x rent if all fields in color are owned
            return rent[houseLevel];
        }
        
        
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getKey(){
        return key;
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
    public void landOnField(Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        super.landOnField(player, pLst, deck, board, gui, lib);
        if (this.owner.equals("")){
            gui.showMessage(String.format(lib.text.get("NotOwned"), price));
            this.owner = player.getName();
            player.addBal(-this.getRent());
            gui.updateBoard(board.getBoard());
            //print - field not bought so you are buying this field
        }
        else if (player.getName().equals(this.owner)){ //player is the owner
            gui.showMessage(lib.text.get("OwnField"));
        }
        else{
            for(Player ownercheck : pLst){
                if(ownercheck.getName().equals(this.owner)){//with for loop it finds player who owns THIS field
                    int sameColorOwned = 0;//counter for how many fields of THIS color has THIS owner
                    for(Field fieldCheck : board.getBoard()){//checks all fields (including this)
                        if(fieldCheck.getColor().equals(this.color) && fieldCheck.getOwner().equals(this.owner)){
                            sameColorOwned++;
                        }
                    }
                    if(sameColorOwned == 2){
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
    }


}
