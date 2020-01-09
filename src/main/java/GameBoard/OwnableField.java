package GameBoard;

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
        for (int i = 0; i < 6; i++){
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

}