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
    }
}
