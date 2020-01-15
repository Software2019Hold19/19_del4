package Main;


import GameBoard.Field;
import GameBoard.GameBoard;
import GameBoard.OwnableField;

public class Player {
    private String name;
    private Account acc = new Account();
    private int fieldNumber = 0;
    private int oldFieldNumber = 0;
    private boolean alive = true;
    private int jailCount = 0;
    private boolean isJailed = false;
    private boolean jailCard = false;
    private int lastRollVal = 0;

    public Player (String name, int Bal){
        this.name = name;
        this.acc.addBalance(Bal);
    }

    public void blink(int pos){ // move directly to pos
        this.fieldNumber = pos;
    }// move directly to pos

    public void move(int val){
        this.lastRollVal = val;
        this.fieldNumber += val;
        if (this.fieldNumber >= 40){
            this.fieldNumber -= 40;
            this.addBal(4000); // income from start
        }
    }

    public String getName(){
        return this.name;
    }

    public int getBal(){
        return acc.getBalance();
    }

    public void addBal(int val){
        acc.addBalance(val);
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public int getOldFieldNumber() {
        return oldFieldNumber;
    }

    public void step() {
        oldFieldNumber++;
        if (oldFieldNumber >= 40) {
            oldFieldNumber = 0;
        }
    }


    public boolean getAlive(){
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public int getLastRollVal(){
        return this.lastRollVal;
    }

    public boolean getIsJailed(){
        return isJailed;
    }

    public void setIsJailed(boolean jailed){
        isJailed = jailed;
    }

    public void addJailTurn(){
        jailCount++;
    }

    public void resetJailTurn(){
        jailCount = 0;
    }


    public int getJailTurn() { return jailCount; }

    public void jail(){
        blink(10);
        setIsJailed(true);
    }

    public boolean getJailCard(){
        return jailCard;
    }

    public void setJailCard(boolean set){
        jailCard = set;
    }

    public OwnableField[] getPlayersFields(OwnableField[] fields){

        OwnableField[] fieldLst = new OwnableField[0];

        for (int i = 0; i < fields.length; i++){
            if (fields[i].getOwner().equals(getName())){
                OwnableField[] tmpLst = new OwnableField[fieldLst.length + 1];

                for (int j = 0; j < fieldLst.length; j++) {
                    tmpLst[j] = fieldLst[j];
                }

                tmpLst[tmpLst.length - 1] = fields[i];
                fieldLst = tmpLst;
            }
        }
        return fieldLst;
    }
}
