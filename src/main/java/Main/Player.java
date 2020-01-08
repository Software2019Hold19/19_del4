package Main;


public class Player {
    String name;
    private Account acc = new Account();
    int fieldNumber = 0;
    int jailCount = 0;
    boolean isJailed = false;

    public Player (String name){
        this.name = name;
    }

    public Player (String name, int Bal){
        this.name = name;
        this.acc.addBalance(Bal);
    }

    public void blink(int pos){ // move directly to pos
        this.fieldNumber = pos;
    }// move directly to pos

    public void move(int val){
        this.fieldNumber += val;
        if (this.fieldNumber >= 24){
            this.fieldNumber -= 24;
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

    public int getJailTurn(){
        return jailCount;
    }
}
