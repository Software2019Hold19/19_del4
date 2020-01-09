package GameBoard;
import GameBoard.GameBoard;
import GUI.GUIController;
import Main.Player;
import Main.Translator;

/**
 * OwnableField
 */
abstract class OwnableField extends Field {

    protected int price;
    
    protected String owner = "";

    public OwnableField(String name, String subName, String desc, String type, String rent) {
        super(name, subName, desc, type);
        String[] rentLst = rent.split(",");
        this.rent = new int[rentLst.length];
        for (int i = 0; i < rentLst.length; i++){
            this.rent[i] = Integer.parseInt(rentLst[i]);
        }
        // TODO Auto-generated constructor stub
    }

    public void ownField () {

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
    
    public int getPrice() {
        return this.price;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public boolean choiceToBuy(Player player, GameBoard board, GUIController gui, Translator lib){
        String buyChoice = gui.getPlayerDropbown(String.format(lib.text.get("ChoiceToBuy"), this.price), "Yes", "No");
        if(buyChoice.equals("Yes")){
            setOwner(player.getName());
        }

    }
    (Player player, Player[] pLst, ChanceDeck deck, GameBoard board, GUIController gui, Translator lib){
        guiHandler(gui, lib);
}