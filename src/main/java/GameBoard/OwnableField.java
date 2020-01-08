package GameBoard;

/**
 * OwnableField
 */
abstract class OwnableField extends Field {

    protected int price;
    
    protected String owner = "";

    public OwnableField(String name, String subName, String desc, String type, int[] rentLst) {
        super(name, subName, desc, type);
        this.rent = new int[6];
        this.rent = rentLst;
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

}