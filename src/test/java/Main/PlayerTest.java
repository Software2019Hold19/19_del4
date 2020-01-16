package Main;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player("name",0);
    Account acc = new Account();


    @org.junit.Test
    public void testBlink() {
        player.blink(23);
        assertEquals(23,player.getFieldNumber());
    }

    @org.junit.Test
    public void testMove() {
        player.move(40);
        assertEquals(4000,player.getBal());
    }
}