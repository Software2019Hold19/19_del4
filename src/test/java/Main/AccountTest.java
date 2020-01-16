package Main;

import GUI.PlayerObserver;
import gui_fields.GUI_Player;
import org.junit.Test;
import gui_main.GUI;
import static org.junit.Assert.*;

public class AccountTest {
    Player player1 = new Player("name", 1000);
    Player[] list = new Player[]{player1};

    PlayerObserver playerOb = new PlayerObserver(list);
    GUI gui = new GUI();
    GUI_Player guiP = new GUI_Player("name1",player1.getBal());

    @Test
    public void testAddBalance() throws InterruptedException {
        player1.addBal(5);
        playerOb.update(gui,list);

        int guiVal = playerOb.getGuiPlayerList()[0].getBalance();
        int pVal = player1.getBal();

        assertEquals(pVal,guiVal);
    }
}