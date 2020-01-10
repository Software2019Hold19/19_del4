package GameBoard;
import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

public class StreetField extends OwnableField {

    private String color;

    private String key; // use in hashmap

    public StreetField(String name, String subName, String desc, String type, String rentStr, String color, String key){
        super(name, subName, desc, type, rentStr); // TODO: edit to price and rent

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
        return rent[level];
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
        //Import LandOnField from ownablefield class
        super.landOnField(player, pLst, deck, board, gui, lib);

        if (this.owner.equals(player.getName())) {
            if (ownsSameColorFields(board, pLst)) {
                chooseToBuyHouses(player, gui, lib);
                System.out.println("owns same color fields");
            }
        }
    }


    private int getHousePrice() {
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

    private boolean ownsSameColorFields(GameBoard board, Player[] pLst){
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

    private void chooseToBuyHouses(Player player, GUIController gui, Translator lib) {

        // priser for huse <2000 1000kr. <4000 2000kr. <6000 3000kr.  ellers 4000kr.
        if (this.level <= 3) {
            int housePrice = getHousePrice();
            String choise = gui.getPlayerDropbown(lib.text.get("Buyhouse") + housePrice + " kr.", lib.text.get("Yes"), lib.text.get("No"));

            if (choise.equals("Ja")) {
                if (player.getBal() > housePrice) {
                    this.level++;
                    player.addBal(-housePrice);
                }
            }
        }
        else if (this.level == 4) {
            int hotelPrice = getHousePrice();
            String choise = gui.getPlayerDropbown(lib.text.get("Buyhotel")+ hotelPrice + " kr.", lib.text.get("Yes"), lib.text.get("No"));

            if (choise.equals("Ja")) {
                if (player.getBal() > hotelPrice) {
                    this.level++;
                    player.addBal(-hotelPrice);
                }
            }
        }
    }
}
