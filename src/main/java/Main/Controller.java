package Main;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import GameBoard.GameBoard;
import GameBoard.OwnableField;
import GameBoard.Field;

import java.awt.*;
import java.io.IOException;

public class Controller {
    Boolean testing = false;
    Translator lib = new Translator("Dansk");
    GameBoard board = new GameBoard(lib);
    GUIController gui = new GUIController(lib, board);
    Player[] pLst;
    ChanceDeck deck = new ChanceDeck(lib, testing, -1);
    Dice dice = new Dice();
    int playerCount;


    public Controller() throws IOException {
        
    }

    // Init players and language
    public void startGame() throws IOException, InterruptedException {
        String selectedL = gui.getPlayerDropdown("Vælg Sprog / Choose Language", "Dansk");
        lib.getLanguage(selectedL);
        gui.updateLanguage(lib);
        board.boardUpdate(lib);
        gui.showMessage(lib.text.get("Welcome"));

        //gui.getGui().getFields()[1].setForeGroundColor(Color.GRAY);

        String playerCountstr = gui.getPlayerDropdown(lib.text.get("NumberOfPlayers"), "3", "4", "5", "6");
        playerCount = Integer.parseInt(playerCountstr);
        int startBal = 30000;

        //gui.getPlayerDropbown("Test", new String[]{"test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2","test1", "test2"});
        //System.out.println(gui.getPlayerInt("TestInt"));
        //name input - repeat if names are the same
        while (true) {
            boolean sameName = false;
            boolean noName = false;
            pLst = new Player[playerCount];
            for (int i = 0; i < playerCount; i++) {
                Player p = new Player(gui.getUserString(String.format(lib.text.get("InputName"), i + 1)), startBal);
                System.out.println(p.getName());
                if (p.getName().equals("")) {
                    noName = true;
                }
                pLst[i] = p;
            }
            if (!noName) {
                for (int i = 0; i < pLst.length; i++) {
                    for (int j = i + 1; j < pLst.length; j++) {
                        if (pLst[i].getName().equals(pLst[j].getName())) {
                            sameName = true;
                            gui.showMessage(lib.text.get("SameName"));
                        }
                    }
                }
            } else {
                gui.showMessage(lib.text.get("NoName"));
            }

            if (!sameName && !noName) {
                break;
            }

        }

        gui.addPlayers(pLst);

        for (Player p : pLst){
            if (p.getName().equals("Oli")) {
                for (int i = 0; i < board.getOwnableBoard().length; i++) {board.getOwnableBoard()[i].setOwner("Oli");}
                gui.updateBoard(board.getOwnableBoard(), pLst);
            }
        }

        playGame();
    }

    private String turnOrder(Player[] pLst){
        int maximum = 0;
        Player[] starter = new Player[0];

        String res = "";

        for (int i = 0; i < pLst.length; i++){
            int[] roll = dice.roll(testing);
            int val = roll[0] + roll[1];
            gui.showDiceOnBoard(roll);
            gui.showMessage(String.format(lib.text.get("TurnOrderRoll"), pLst[i].getName(), val));
            if (val > maximum){
                maximum = val;
                starter = new Player[] {pLst[i]};
            } else if (val == maximum){
                Player[] tmpLst = new Player[starter.length + 1];
                for (int j = 0; j < starter.length; j++){
                    tmpLst[j] = starter[j];
                }
                tmpLst[tmpLst.length - 1] = pLst[i];
                starter = new Player[tmpLst.length];
                for (int j = 0; j < starter.length; j++){
                    starter[j] = tmpLst[j];
                }

            }
        }

        if (starter.length > 1) {
            gui.showMessage(lib.text.get("TurnOrderRedo"));
            res = turnOrder(starter);
        } else {
            gui.showMessage(String.format(lib.text.get("TurnOrderWinner"), starter[0].getName()));
            res = starter[0].getName();
        }
        return res;
    }

    private void playGame() throws InterruptedException {
        gui.showMessage(lib.text.get("TurnOrderStart"));
        String starterName = turnOrder(pLst);
        int turnCount = 0;
        for (int i = 0; i < pLst.length; i++){
            if (pLst[i].getName().equals(starterName)) {
                turnCount = i;
            }
        }
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
        gui.updatePlayers(pLst);
        gui.updateBoard(board.getOwnableBoard(), pLst);
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
    private boolean isOnePlayerLeft(Translator lib) throws InterruptedException {    //dette var "hasPlayerNotWon" før

        int aliveCount = 0;
        for (Player p : pLst) {
            // if player has no money then die
            if (p.getBal() < 0 && p.getAlive()) {
                gui.showMessage(String.format(lib.text.get("TrialStart"), p.getName()));
                while (deathTrial(p));


        //        gui.showMessage(String.format(lib.text.get("EndOfGame"), p.getName()));
            }else if(p.getBal() == 0 && !p.getAlive()){

            }
            if (p.getAlive()){
                aliveCount++;
            } else {
                if (p.getPlayersFields(board.getOwnableBoard()).length > 0) {
                    for (OwnableField field : p.getPlayersFields(board.getOwnableBoard())) {
                        if (!testing) {
                            gui.showMessage(String.format(lib.text.get("GiveUpAuction"), p.getName(), field.getName()));
                            field.auction(p, pLst, gui, lib);
                            gui.updateBoard(board.getOwnableBoard(), pLst);
                        } else {
                            field.setOwner("");
                            gui.updateBoard(board.getOwnableBoard(), pLst);
                        }
                    }
                }
            }

        //        gui.showMessage(String.format(lib.text.get("WinnerByDefault"), p.getName()));
        }
        if(aliveCount == 1){
            return true;
        }
        else {
            return false;
        }
        //return !isGameFinished;
    }

    private Boolean deathTrial(Player p) throws InterruptedException {
        if (!testing) {
            managementStream(p, board, "Return");
            if (!p.getAlive()) {
                return false;
            } else if (p.getPlayersFields(board.getOwnableBoard()).length == 0 && p.getBal() < 0) {
                gui.showMessage(lib.text.get("TrialLose"));
                p.kill(); //Kill Confirmed
                return false;
            } else if (p.getBal() < 0) {
                gui.showMessage(lib.text.get("TrialTurn"));
                return true;
            } else {
                gui.showMessage(lib.text.get("TrialWin"));
                return false;
            }
        } else {
            p.kill();
            return false;
        }
    }

    private void playerTurn(Player p) throws InterruptedException {

        int cntDoubleDiceRoll = 0;
        boolean playAgain = false;

        gui.showMessage(String.format(lib.text.get("Turn"), p.getName()));

        do {
            if (playAgain && p.getBal() > 0) {
                gui.showMessage(String.format(lib.text.get("RollDoubleTurn"), p.getName()));
            } else if (p.getBal() <= 0){
                gui.showMessage(String.format(lib.text.get("TrialStart"), p.getName()));
                while (deathTrial(p));
            }

            if (p.getAlive()) {

                if (!p.getIsJailed()) { //If the player is not jailed

                    managementStream(p, board, "RollChoice");
                    if (!p.getAlive()) { //If player gives up in managementStream, they shouldn't get a turn
                        playAgain = false;
                    } else {
                        Boolean manual = false; //TODO: FOR MANUAL DICE ROLLS!!! MAKE SURE TO LEAVE ON FALSE!!!!!!!!!!!!!!!!!! (TODO FOR COLOR)
                        int[] diceRoll = dice.roll(testing);
                        if (manual) {
                            int val = Integer.parseInt(gui.getPlayerDropdown("__MANUEL__ Dice", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2"));
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
                            if (cntDoubleDiceRoll == 3) {
                                playAgain = false;
                            }
                        } else {
                            playAgain = false;
                        }

                        System.out.println(p.getName() + " tur nr: " + cntDoubleDiceRoll);


                        if (cntDoubleDiceRoll != 3) {
                            p.move(diceRoll[0] + diceRoll[1]);
                            gui.updatePlayers(pLst);
                            //       board.getBoard()[p.getFieldNumber()].guiHandler(gui, lib);

                            //If manual is true, then manually draw specific cards
                            //else run as normal
                            if (manual && board.getBoard()[p.getFieldNumber()].getType() == "chance") { // if manual=true and the field is a chance field
                                int val = Integer.parseInt(gui.getPlayerDropdown(lib.text.get("ChanceManualMsg"), "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"));
                                deck = new ChanceDeck(lib, true, val);
                                board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                gui.updateBoard(board.getOwnableBoard(), pLst);
                            } else {
                                board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                gui.updateBoard(board.getOwnableBoard(), pLst);
                                gui.updatePlayers(pLst);
                            }
                            if (p.getIsJailed()) {
                                playAgain = false;
                            }
                        } else {
                            playAgain = false;
                            gui.showMessage(lib.text.get("JailTripleDouble"));
                            p.jail();
                            gui.updatePlayers(pLst);
                        }
                    }

                }
                else if (p.getIsJailed()) {           // The player is jailed and gets a choice the next 3 turns

                    gui.updatePlayers(pLst);
                    playAgain = false;

                    int caseSwitch = 0;
                    String jailOptionStr = "";
                    String roll = lib.text.get("JailRoll");
                    String pay = lib.text.get("JailPay");
                    String jailCard = lib.text.get("JailCard");

                    if (!p.getJailCard()) {          //if the player doesn't have a "Get out of jail" chance card
                        jailOptionStr = gui.getPlayerDropdown(lib.text.get("YourOptionsJail"), roll, pay);
                    } else {                           //if the player has a "Get out of jail" chance card
                        jailOptionStr = gui.getPlayerDropdown(lib.text.get("YourOptionsJail"), jailCard, roll, pay);
                    }
                    if (jailOptionStr == roll) {
                        caseSwitch = 1;
                    } else if (jailOptionStr == pay) {
                        caseSwitch = 2;
                    } else {
                        caseSwitch = 3;
                    }

                    switch (caseSwitch) {

                        case (1):                                    //if the player chooses to roll for a pair
                            int tries = 3;
                            while (tries > 0) {
                                int[] diceRoll = dice.roll(testing);
                                gui.showDiceOnBoard(diceRoll);

                                if (diceRoll[0] == diceRoll[1]) {
                                    gui.showMessage(String.format(lib.text.get("JailRollWin"), diceRoll[0], diceRoll[1]));
                                    p.setIsJailed(false);
                                    p.resetJailTurn();
                                    p.move(diceRoll[0] + diceRoll[1]);

                                    gui.updatePlayers(pLst);
                                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                    gui.updatePlayers(pLst);
                                    break;
                                } else if (--tries == 0) {          //if the player doesn't get a pair after 3 turns
                                    gui.showMessage(String.format(lib.text.get("JailRollTurnLast"), diceRoll[0], diceRoll[1]));
                                } else {
                                    gui.showMessage(String.format(lib.text.get("JailRollTurn"), diceRoll[0], diceRoll[1], tries));
                                }
                            }
                            break;

                        case (2):                                    //if the player chooses to pay
                            p.setIsJailed(false);
                            p.resetJailTurn();
                            p.addBal(-1000);

                            int[] diceRoll2 = dice.roll(testing);
                            gui.showDiceOnBoard(diceRoll2);
                            p.move(diceRoll2[0] + diceRoll2[1]);

                            gui.updatePlayers(pLst);
                            board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                            gui.updatePlayers(pLst);

                            gui.showMessage(lib.text.get("PayedEscape"));

                            break;

                        case (3):                                    //if the player has a "Get out of jail" chance card
                            p.setIsJailed(false);
                            p.resetJailTurn();
                            p.setJailCard(false);

                            int[] diceRoll3 = dice.roll(testing);
                            gui.showDiceOnBoard(diceRoll3);
                            p.move(diceRoll3[0] + diceRoll3[1]);

                            gui.updatePlayers(pLst);
                            board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                            gui.updatePlayers(pLst);
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + jailOptionStr);
                    }
                }
            }
        } while (playAgain);
    }

    public void setTesting() {
        testing = !testing;
        deck = new ChanceDeck(lib, testing, -1);
        gui.setTesting(testing);
    }

    private void propertyMangement(Player player, GameBoard board, String end) throws InterruptedException {
        
            // Choose a field
            // Sell house or choose a new field
            // Roll
            String playerNextStep = "";
            int propertyIndex = 0;
           
            boolean chooseField = true;
        
            do {
                if (end.equals("Return") || player.getBal() > 0) {
                    OwnableField[] playersFields = player.getPlayersFields(board.getOwnableBoard());
                    gui.updateBoard(playersFields, pLst);
                    gui.updatePlayers(pLst);

                    if (chooseField) {
                        String fieldNames[] = new String[playersFields.length];

                        for (int i = 0; i < playersFields.length; i++) {
                            fieldNames[i] = playersFields[i].getName();
                        }

                        gui.updateBoard(board.getOwnableBoard(), pLst);
                        if (fieldNames.length > 0) {
                            String selectedFieldName = gui.getPlayerDropdown(lib.text.get("ChooseAField"), fieldNames);
                            propertyIndex = getOwnableFieldIndex(selectedFieldName, playersFields);
                        } else {
                            gui.showMessage(lib.text.get("NoFields"));
                        }
                        chooseField = false;
                    }

                    if (playersFields.length != 0 && !playersFields[propertyIndex].getMortgage()) {
                        playerNextStep = gui.getPlayerBtn(lib.text.get("ChooseNext"), lib.text.get("SellHouse"), lib.text.get("MortgageProp"), lib.text.get("ChooseNewField"), lib.text.get("Back"));
                    } else {
                        playerNextStep = gui.getPlayerBtn(lib.text.get("ChooseNext"), lib.text.get("SellHouse"), lib.text.get("ReopenProp"), lib.text.get("ChooseNewField"), lib.text.get("Back"));
                    }


                    // sell house
                    if (playerNextStep.equals(lib.text.get("SellHouse"))) {
                        // checks if there is any houses on the field
                        if (playersFields[propertyIndex].getHouseLevel() <= 0) {
                            gui.showMessage(lib.text.get("NoHouses"));
                            String sellFieldAnwser = gui.getPlayerBtn(lib.text.get("Sure"), lib.text.get("No"), lib.text.get("Yes"));

                            if (sellFieldAnwser.equals(lib.text.get("Yes"))) {
                                playersFields[propertyIndex].sellHouseAndHotel(player, playersFields);
                                if (playersFields.length != 0) {
                                    chooseField = true;
                                }
                            }

                        }
                        // Sælger et hus
                        else {
                            playersFields[propertyIndex].sellHouseAndHotel(player, playersFields);
                        /*playersFields[propertyIndex].minusOneLevel();
                        player.addBal(playersFields[propertyIndex].getHousePrice());*/
                        }
                    }
                    //Mortage a property
                    else if (playerNextStep.equals(lib.text.get("MortgageProp"))) {
                        playersFields[propertyIndex].setMortgage(true);
                        int price = playersFields[propertyIndex].getPrice();
                        double input = price * 0.5;
                        int money = (int) input;
                        player.addBal(money);
                        int boardIndex = getFieldIndex(playersFields[propertyIndex].getName(), board.getBoard());
                        gui.getGui().getFields()[boardIndex].setForeGroundColor(Color.GRAY);
                    }
                    //Reopen properties
                    else if (playerNextStep.equals(lib.text.get("ReopenProp"))) {
                        int price = playersFields[propertyIndex].getPrice();
                        double input = price * 0.55;
                        int money = (int) input;
                        if (player.getBal() > money) {
                            playersFields[propertyIndex].setMortgage(false);
                            player.addBal(-money);
                            int boardIndex = getFieldIndex(playersFields[propertyIndex].getName(), board.getBoard());
                            Color color = Color.BLACK;
                            if (playersFields[propertyIndex].getType().equals("brewery"))
                                color = Color.WHITE;
                            gui.getGui().getFields()[boardIndex].setForeGroundColor(color);
                        } else {
                            gui.showMessage(lib.text.get("ReopenPropNoMoney"));
                        }
                    }
                    // choose new field
                    else if (playerNextStep.equals(lib.text.get("ChooseNewField"))) {
                        if (playersFields.length != 0) {
                            chooseField = true;
                        }
                    }
                } else {
                    gui.updatePlayers(pLst);
                    gui.showMessage(String.format(lib.text.get("TrialStart"), player.getName()));
                    while (deathTrial(player));
                }

            } while (!playerNextStep.equals(lib.text.get("Back"))); // while not roll
    }

    public int getOwnableFieldIndex(String name, OwnableField[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                return i;
            }
        }

        return 0;
    }

    public int getFieldIndex(String name, Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                return i;
            }
        }

        return 0;
    }

    private void managementStream(Player p, GameBoard board, String end) throws InterruptedException {
        while(true) {
            String inputBtn = gui.getPlayerBtn(String.format(lib.text.get("MessagePM"), p.getName()), lib.text.get("PM"), lib.text.get("GiveUp"), lib.text.get(end));
            if (inputBtn.equals(lib.text.get("PM"))) {
                if (p.getPlayersFields(board.getOwnableBoard()).length < 1){
                    gui.showMessage(lib.text.get("NoFields"));
                }
                else {
                    propertyMangement(p, board, end);
                }
            }
            while (inputBtn.equals(lib.text.get("GiveUp"))) {
                if (!testing) {
                    String answer = gui.getPlayerBtn(lib.text.get("Sure"), lib.text.get("No"), lib.text.get("Yes"));
                    if (answer.equals(lib.text.get("Yes"))) {
                        p.kill();
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (inputBtn.equals(lib.text.get(end))){
                break;
            }
            if (!p.getAlive()){
                gui.showMessage(String.format(lib.text.get("PlayerGiveUp"), p.getName()));
                gui.updateBoard(board.getOwnableBoard(), pLst);
                gui.updatePlayers(pLst);
                break;
            }
        }  
     }
}

