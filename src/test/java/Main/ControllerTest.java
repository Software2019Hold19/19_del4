package Main;

import org.junit.Test;
import stub.StubController;

import java.io.IOException;

import static org.junit.Assert.*;

public class ControllerTest {
    StubController stubController = new StubController();
    int testNum = 6;
    String testStr = testNum + "";

    String[] getNames(){
        String[] names = new String[testNum];
        for(int i = 0; i < testNum; i++){
            names[i] = i + 1 +"";
        }
        return names;
    }

    @Test
    public void testPlayerSelection() throws IOException, InterruptedException {

        String[] names = getNames();
        String[] pNames = new String[testNum];
        Player[] pLst;

        stubController.startGame(testStr, names, true, 0);
        pLst = stubController.getPLst();
        for(int i = 0; i < testNum; i++){
            pNames[i] = pLst[i].getName();
        }

        assertArrayEquals(names, pNames);

    }

    @Test
    public void testJail() throws IOException, InterruptedException {
        //testNum = 3;
        stubController.startGame(testStr, getNames(), false, 1);

    }


    public ControllerTest() throws IOException {
    }
}