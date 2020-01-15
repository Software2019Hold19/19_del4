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

    @Test
    public void testJail() throws IOException, InterruptedException {
        initVariables(3);
        stubController.startGame(testStr, getNames(), true, 1); //Numbers 1 - 3

    }


    public ControllerTest() throws IOException {
    }
}