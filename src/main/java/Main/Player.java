package Main;


public class Player {
    String name;
    private Account acc = new Account();
    int fieldNumber = 0;

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
        if (this.fieldNumber >= 40){
            this.fieldNumber -= 40;
            this.addBal(2); // income from start
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
}
