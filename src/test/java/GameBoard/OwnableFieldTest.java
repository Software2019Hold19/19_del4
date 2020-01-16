package GameBoard;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import Main.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class OwnableFieldTest{

    @Test
    public void testChoiceToBuy() throws IOException, InterruptedException {
        ControllerTest coTest = new ControllerTest();
        GameBoard board = coTest.getBoard();
        GUIController gui = coTest.getGUI();
        Translator lib = coTest.getLib();
        ChanceDeck deck = coTest.getDeck();
        Player p1, p2;


        Player[] pLst = coTest.getPlayers(2, 1000);
        p1 = pLst[0]; p2 = pLst[1];

        OwnableField[] fields = board.getOwnableBoard();
        fields[0].setOwner(p2.getName());

        p1.blink(0);

        p1.move(1);

        board.getBoard()[1].landOnField(p1, pLst, deck, board, gui, lib);
        //board.getBoard()[p1.getFieldNumber()].landOnField(p1, pLst, deck, board, gui, lib);

        System.out.println("P1 bal: "+ p1.getBal() +" Name: "+ p1.getName() +"\nP2 bal: "+ p2.getBal() +" Name: "+ p2.getName());
        System.out.println("Field 1\nOwner: "+ fields[0].getOwner());
    }

}