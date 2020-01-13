package Main;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void playThrough() throws IOException, InterruptedException {
        Controller co = new Controller();
        co.setTesting();
        co.startGame();
        assertEquals(1,1);
    }

}