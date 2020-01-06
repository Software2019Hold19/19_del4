package Main;

public class Account {
    private int balance;

    public Account(){
        balance = 0;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addBalance(int val){
        this.balance += val;
        if(this.balance < 0){ this.balance = 0; }
    }
}
