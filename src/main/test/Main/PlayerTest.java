package Main;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player("name",0);
    Account acc = new Account();


    @org.junit.Test
    public void blink() {
        player.blink(23);
        assertEquals(23,player.fieldNumber);
    }

    @org.junit.Test
    public void move() {
        player.move(24);
        assertEquals(2,player.getBal());
    }
}