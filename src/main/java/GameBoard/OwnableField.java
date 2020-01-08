package GameBoard;

/**
 * OwnableField
 */
abstract class OwnableField extends Field {

    protected int price;
    protected int rent[];
    protected String owner = "";

    public OwnableField(String name, String subName, String desc, String type) {
        super(name, subName, desc, type);
        // TODO Auto-generated constructor stub
    }

    public void ownField () {

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