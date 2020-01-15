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

    void initVariables(int testNum){
        this.testNum = testNum;
        testStr = testNum + "";
    }

    @Test
    public void testPlayerSelection() throws IOException, InterruptedException {

        initVariables(6);

        String[] names = getNames();
        String[] pNames = new String[testNum];
        Player[] pLst;

        stubController.startGame(testStr, names, false, 0); //Number 0 for no jail test
        pLst = stubController.getPLst();
        for(int i = 0; i < testNum; i++){
            pNames[i] = pLst[i].getName();
        }

        assertArrayEquals(names, pNames);

    }

    @Test public void GetOutOfJail() throws IOException, InterruptedException {
        testJail1();
        testJail2();
        testJail3();
    }

    @Test
    public void testJail1() throws IOException, InterruptedException {
        initVariables(2);

        stubController.startGame(testStr, getNames(), true, 1); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
    }

    @Test
    public void testJail2() throws IOException, InterruptedException {
        initVariables(2);

        stubController.startGame(testStr, getNames(), true, 2); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
    }

    @Test
    public void testJail3() throws IOException, InterruptedException {
        initVariables(2);

        stubController.startGame(testStr, getNames(), true, 3); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
    }


    public ControllerTest() throws IOException {
    }
}