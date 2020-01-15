package GameBoard;

import Main.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class OwnableFieldTest{

    @Test
    public void testChoiceToBuy() throws IOException {
        ControllerTest coTest = new ControllerTest();
        Player p1, p2;
        //controller.getOwnableFieldIndex();

        //board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);

        Player[] pLst = coTest.getPlayers(2);
        p1 = pLst[0]; p2 = pLst[1];


    }

}