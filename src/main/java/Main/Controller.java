package Main;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import GameBoard.GameBoard;
import java.io.IOException;

public class Controller {
    Boolean testing = false;
    Translator lib = new Translator("Dansk");
    GameBoard board = new GameBoard(lib);
    GUIController gui = new GUIController(lib, board);
    Player[] pLst;
    ChanceDeck deck = new ChanceDeck(lib, testing);
    Dice dice = new Dice();
    int playerCount;

    
    public Controller() throws IOException {
        
    }

    // Init players and language
    public void startGame() throws IOException {
        String selectedL = gui.getPlayerDropbown("Vælg Sprog / Choose Language", "Dansk");
        lib.getLanguage(selectedL);
        gui.updateLanguage(lib);
        board.boardUpdate(lib);
        gui.showMessage(lib.text.get("Welcome"));

        String playerCountstr = gui.getPlayerDropbown(lib.text.get("NumberOfPlayers"), "3", "4", "5", "6");
        playerCount = Integer.parseInt(playerCountstr);
        int startBal = 30000;

        while (true) {

            boolean sameName = false;
            pLst = new Player[playerCount];
            for (int i = 0; i < playerCount; i++) {
                Player p = new Player(gui.getUserString(String.format(lib.text.get("InputName"), i + 1)), startBal);
                pLst[i] = p;
            }

            for (int i = 0; i < pLst.length; i++) {
                for (int j = i + 1; j < pLst.length; j++) {
                    if (pLst[i].getName().equals(pLst[j].getName())) {
                        sameName = true;
                        gui.showMessage(lib.text.get("SameName"));
                    }
                }
            }

            if (!sameName) {
                break;
            }

        }

        gui.addPlayers(pLst);

        playGame();
    }

    private void playGame() {
        int turnCount = 0;
        int turnCountTotal = 0;

        while (!isOnePlayerLeft(lib)) {
            gui.updateBoard(board.getOwnableBoard(), pLst);
            if(turnCount >= playerCount)
                turnCount = 0;
            while(!pLst[turnCount].getAlive()){
                turnCount++;
                if(turnCount >= playerCount)
                    turnCount = 0;
            }

            gui.showMessage(String.format(lib.text.get("Turn"), pLst[turnCount].getName()));

            playerTurn(pLst[turnCount]);

            turnCount++;
            turnCountTotal++;

            if (turnCountTotal % 100 == 0) {
                System.out.println("Turn Count total: " + turnCountTotal);
            }
        }
        if (testing) {
            setTesting();
        }
        int max = pLst[0].getBal();
        String winner = pLst[0].getName();
        for (Player p : pLst){
            if (p.getBal() > max){
                winner = p.getName();
                max = p.getBal();
            }
        }
        System.out.println(turnCountTotal);
        gui.showMessage(String.format(lib.text.get("Winner"), winner));
    }

    // kills players that are on 0 money and checks how many are left.
    private boolean isOnePlayerLeft(Translator lib) {    //dette var "hasPlayerNotWon" før

        int aliveCount = 0;
        for (Player p : pLst) {
            // if player has no money then die
            if (p.getBal() == 0) {
                p.kill();


        //        gui.showMessage(String.format(lib.text.get("EndOfGame"), p.getName()));
            }
            if (p.getAlive()){
                aliveCount++;
            }

        //        gui.showMessage(String.format(lib.text.get("WinnerByDefault"), p.getName()));
        }
        if(aliveCount==1){
            return true;
        }
        else {
            return false;
        }
        //return !isGameFinished;
    }


    private void playerTurn(Player p) {
        
        int cntDoubleDiceRoll = 0;
        boolean playAgain = false;


        if(!p.getIsJailed()) {  //If the player is not jailed
            int[] diceRoll = dice.roll(testing);

            gui.showDiceOnBoard(diceRoll);

            p.move(diceRoll[0] + diceRoll[1]);


            gui.updatePlayers(pLst);
            //       board.getBoard()[p.getFieldNumber()].guiHandler(gui, lib);
            board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);


            gui.updatePlayers(pLst);
        }else if(p.getJailTurn() < 3){           // The player is jailed and gets a choice the next 3 turns

            int caseCounter = 0;
            String roll = lib.text.get("Roll"); String pay = lib.text.get("Pay");

            String jailOptionStr = gui.getPlayerDropbown(lib.text.get("YourOptionsJail"),roll, pay);

            if(jailOptionStr == roll) { caseCounter = 1; }
            else if(jailOptionStr == pay) { caseCounter = 2; }

            switch(caseCounter) {

                case(1):
                    int[] diceRoll = dice.roll(testing);
                    gui.showDiceOnBoard(diceRoll);

                    if(diceRoll[0] == diceRoll[1]){
                        p.setIsJailed(false);
                        p.resetJailTurn();
                        p.move(diceRoll[0] + diceRoll[1]);

                        gui.updatePlayers(pLst);
                        board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                        gui.updatePlayers(pLst);
                    }
                    else if(p.getJailTurn() == 3){
                        p.setIsJailed(false);
                        p.resetJailTurn();
                        p.addBal(-1000);
                        p.move(diceRoll[0] + diceRoll[1]);

                        gui.updatePlayers(pLst);
                        board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                        gui.updatePlayers(pLst);
                    }
                    else { p.addJailTurn(); }
                    break;

                case(2):
                    p.setIsJailed(false);
                    p.resetJailTurn();
                    p.addBal(-1000);

                    int[] diceRoll2 = dice.roll(testing);
                    gui.showDiceOnBoard(diceRoll2);
                    p.move(diceRoll2[0] + diceRoll2[1]);

                    gui.updatePlayers(pLst);
                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                    gui.updatePlayers(pLst);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + jailOptionStr);
            }
        }
                
        do {
            if(!p.getIsJailed()) {  //If the player is not jailed
                Boolean manual = true; //TODO: FOR MANUAL DICE ROLLS!!! MAKE SURE TO LEAVE ON FALSE!!!!!!!!!!!!!!!!!! (TODO FOR COLOR)
                int[] diceRoll = dice.roll(testing);
                if (manual) {
                    int val = Integer.parseInt(gui.getPlayerDropbown("__MANUEL__ Dice", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2"));
                    if (val < 7) {
                        diceRoll[0] = val - 1;
                        diceRoll[1] = 1;
                    } else {
                        diceRoll[0] = val - 6;
                        diceRoll[1] = 6;
                    }
                }

                gui.showDiceOnBoard(diceRoll);

                if (diceRoll[0] == diceRoll[1]) {
                    cntDoubleDiceRoll++;
                    playAgain = true;
                    System.out.println("ekstra tur: " + cntDoubleDiceRoll);
                }

                if (cntDoubleDiceRoll != 3) {
                    p.move(diceRoll[0] + diceRoll[1]);

                    gui.updatePlayers(pLst);
                    //       board.getBoard()[p.getFieldNumber()].guiHandler(gui, lib);
                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                }
                else {
                    playAgain = false;
                    // TODO: player go to jail
                }

                gui.updatePlayers(pLst);
            
            } else{                              // If the player is in jail

                while(p.getJailTurn() <= 3) {
                    int[] diceRoll = dice.roll(testing);
                    gui.showDiceOnBoard(diceRoll);

                    if (diceRoll[0] == diceRoll[1]) {
                        p.move(diceRoll[0] + diceRoll[1]);

                        gui.updatePlayers(pLst);
                        board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                        gui.updatePlayers(pLst);
                        p.setIsJailed(false);
                        break;

                    }else if(p.getJailTurn() == 3){
                        p.addBal(-1000);
                        p.setIsJailed(false);


                    }else{ p.addJailTurn(); }
                }
            }
        } while (playAgain);
    }

    public void setTesting() {
        testing = !testing;
        deck = new ChanceDeck(lib, testing);
        gui.setTesting(testing);
    }
    
}


