package GameBoard;

/**
 * FerryField
 */
public class FerryField extends OwnableField{

    public FerryField(String name, String subName, String desc, String type, String rent, String key) {
        super(name, subName, desc, type, rent, key);
        this.key = key;
    }

    @Override
    public int getRent(GameBoard board){
        int ownedCount = 0;
        //check how many ferries this ferryowner got.
        for (OwnableField field : board.getOwnableBoard()) {
            if(this.owner.equals(field.getOwner()) && field.getType().equals("ferry")){
                ownedCount++;
            }
        }
        return this.rent[ownedCount-1]; //minus 1 to cuz index...
    }

}