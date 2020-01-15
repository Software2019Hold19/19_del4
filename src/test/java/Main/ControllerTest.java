package Main;

import GUI.GUIController;
import GameBoard.GameBoard;
import org.junit.Test;
import stub.StubController;

import java.io.IOException;

import static org.junit.Assert.*;

public class ControllerTest {
    StubController stubController = new StubController();
    Controller controller = new Controller();
    int testNum = 6;
    String testStr = testNum + "";

    String[] getNames(){
        String[] names = new String[testNum];
        for(int i = 0; i < testNum; i++){
            names[i] = i +"";
        }
        return names;
    }

    public void initVariables(int testNum){
        this.testNum = testNum;
        testStr = testNum + "";

        controller.gui.setTestNames(testNum);
        controller.setTesting();
        controller.playerCount = testNum;
        controller.pLst = new Player[controller.playerCount];
        for(int i = 0; i < controller.playerCount; i++){
            controller.pLst[i] = new Player(controller.gui.getUserString(""), 1000);
        }
    }

    public Player[] getPlayers(int testNum){
        initVariables(testNum);
        return controller.pLst;
    }

    @Test
    public void testPlayerSelection() throws IOException, InterruptedException {

        initVariables(3);

        String[] names = getNames();
        String[] pNames = new String[testNum];
        int count = testNum;

        controller.gui.getUserString("");
        for(int i = 0; i < controller.pLst.length; i++){
            pNames[i] = controller.pLst[i].getName();
            names[i] = count+"";
            count++;
        }

        for(int i = 0; i < controller.pLst.length; i++){
            System.out.println("Expected: "+ names[i] +" \t|\t Actual: "+ pNames[i]);
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

        stubController.startGame(true, testStr, getNames(), true, 1); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
        System.out.println("---------------------\nPlayer in jail: "+
                stubController.playerJailed +"\n---------------------\n");
    }

    @Test
    public void testJail2() throws IOException, InterruptedException {
        initVariables(2);

        stubController.startGame(true, testStr, getNames(), true, 2); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
        System.out.println("---------------------\nPlayer in jail: "+
                stubController.playerJailed +"\n---------------------\n");
    }

    @Test
    public void testJail3() throws IOException, InterruptedException {
        initVariables(2);

        stubController.startGame(true, testStr, getNames(), true, 3); //Numbers 1 - 3
        assertEquals(stubController.playerJailed, false);
        System.out.println("---------------------\nPlayer in jail: "+
                stubController.playerJailed +"\n---------------------\n");
    }

    @Test
    public void testChoiceToBuy() throws IOException, InterruptedException {

        controller.setTesting();




        //stubController.startGame(true, testStr, getNames(), true, 0);
        //pLst = stubController.getPLst();



    }


    public ControllerTest() throws IOException {
    }
}