package stub;

import ChanceDeck.ChanceDeck;
import GUI.GUIController;
import GameBoard.*;
import Main.Dice;
import Main.Player;
import Main.Translator;

import java.awt.*;
import java.io.IOException;

public class StubController {

    Boolean testing = false;
    Boolean jTest = false;
    Translator lib = new Translator("Dansk");
    GameBoard board = new GameBoard(lib);
    GUIController gui = new GUIController(lib, board);
    Player[] pLst;
    ChanceDeck deck = new ChanceDeck(lib, testing, -1);
    Dice dice = new Dice();
    int playerCount;
    int jailCaseNum;
    public boolean playerJailed = true;


    public StubController() throws IOException {

    }

    public Player[] getPLst(){
        return  pLst;
    }

    // Init players and language
    public void startGame(String amount, String[] names, Boolean play, Integer jailCaseNum) throws IOException, InterruptedException {
        lib.getLanguage("Dansk");
        gui.updateLanguage(lib);
        this.jailCaseNum = jailCaseNum;


        //gui.getGui().getFields()[1].setForeGroundColor(Color.GRAY);

        String playerCountstr = amount;
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
                Player p = new Player(String.format(names[i], i + 1), startBal);
                System.out.println(p.getName());

                if(jailCaseNum > 0){ p.setIsJailed(true); } //Jail test

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
                            System.out.println(lib.text.get("SameName"));
                        }
                    }
                }
            } else {
                System.out.println(lib.text.get("NoName"));
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

        if(play){
            playGame();
        }

    }

    private String turnOrder(Player[] pLst){
        int maximum = 0;
        Player[] starter = new Player[0];

        String res = "";

        for (int i = 0; i < pLst.length; i++){
            int[] roll = dice.roll(testing);
            int val = roll[0] + roll[1];
            System.out.println(String.format(lib.text.get("TurnOrderRoll"), pLst[i].getName(), val));
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
            System.out.println(lib.text.get("TurnOrderRedo"));
            res = turnOrder(starter);
        } else {
            System.out.println(String.format(lib.text.get("TurnOrderWinner"), starter[0].getName()));
            res = starter[0].getName();
        }
        return res;
    }

    private void playGame() throws InterruptedException {
        System.out.println(lib.text.get("TurnOrderStart"));
        String starterName = turnOrder(pLst);
        int turnCount = 0;
        for (int i = 0; i < pLst.length; i++){
            if (pLst[i].getName().equals(starterName)) {
                turnCount = i;
            }
        }
        int turnCountTotal = 0;

        while (!isOnePlayerLeft(lib)) {
            //gui.updateBoard(board.getOwnableBoard(), pLst);
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
        int max = pLst[0].getBal();
        String winner = pLst[0].getName();
        for (Player p : pLst){
            if (p.getBal() > max){
                winner = p.getName();
                max = p.getBal();
            }
        }
        System.out.println(turnCountTotal);
        System.out.println(String.format(lib.text.get("Winner"), winner));
    }

    // kills players that are on 0 money and checks how many are left.
    private boolean isOnePlayerLeft(Translator lib) throws InterruptedException {    //dette var "hasPlayerNotWon" før

        int aliveCount = 0;
        for (Player p : pLst) {
            // if player has no money then die
            if (p.getBal() < 0) {
                System.out.println(String.format(lib.text.get("TriaStart"), p.getName()));
                while (deathTrial(p));


                //        gui.showMessage(String.format(lib.text.get("EndOfGame"), p.getName()));
            }else if(p.getBal() == 0 && !p.getAlive()){

            }
            if (p.getAlive()){
                aliveCount++;
            } else {
                if (p.getPlayersFields(board.getOwnableBoard()).length > 0) {
                    for (OwnableField field : p.getPlayersFields(board.getOwnableBoard())) {
                        System.out.println(String.format(lib.text.get("GiveUpAuction"), p.getName(), field.getName()));
                        field.auction(p, pLst, gui, lib);
                    }
                }
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

    private Boolean deathTrial(Player p) throws InterruptedException {
        managementStream(p, board, "Return");
        if (!p.getAlive()){
            return false;
        } else if (p.getPlayersFields(board.getOwnableBoard()).length == 0 && p.getBal() < 0){
            System.out.println(lib.text.get("TrialLose"));
            p.kill(); //Kill Confirmed
            return false;
        } else if (p.getBal() < 0) {
            System.out.println(lib.text.get("TrialTurn"));
            return true;
        } else {
            System.out.println(lib.text.get("TrialWin"));
            return false;
        }
    }

    private void playerTurn(Player p) throws InterruptedException {

        int cntDoubleDiceRoll = 0;
        boolean playAgain = false;

        System.out.println(String.format(lib.text.get("Turn"), p.getName()));

        do {
            if (playAgain) {
                System.out.println(String.format(lib.text.get("RollDoubleTurn"), p.getName()));
            }


            if (p.getAlive()) {  //If the player is not jailed

                if (!p.getIsJailed()) {
                    if(jailCaseNum <= 0){
                        managementStream(p, board, "RollChoice");
                    }

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


                    if (diceRoll[0] == diceRoll[1]) {
                        cntDoubleDiceRoll++;
                        playAgain = true;
                        if(cntDoubleDiceRoll == 3){
                            playAgain = false;
                        }
                    } else {
                        playAgain = false;
                    }

                    System.out.println(p.getName() + " tur nr: " + cntDoubleDiceRoll);


                    if (cntDoubleDiceRoll != 3) {
                        p.move(diceRoll[0] + diceRoll[1]);
                        //gui.updatePlayers(pLst);
                        //       board.getBoard()[p.getFieldNumber()].guiHandler(gui, lib);

                        //If manual is true, then manually draw specific cards
                        //else run as normal
                        if(manual && board.getBoard()[p.getFieldNumber()].getType() == "chance"){ // if manual=true and the field is a chance field
                            int val = Integer.parseInt(gui.getPlayerDropdown(lib.text.get("ChanceManualMsg"), "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"));
                            deck = new ChanceDeck(lib, testing, val);
                            board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                            //gui.updateBoard(board.getOwnableBoard(), pLst);
                        }else {
                            board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                            //gui.updateBoard(board.getOwnableBoard(), pLst);
                        }
                        if(p.getIsJailed()){
                            playAgain = false;
                        }
                    } else {
                        playAgain = false;
                        System.out.println(lib.text.get("JailTripleDouble"));
                        p.jail();
                        //gui.updatePlayers(pLst);
                    }

                }
                if (p.getIsJailed() && p.getJailTurn() < 4) {           // The player is jailed and gets a choice the next 3 turns

                    //gui.updatePlayers(pLst);
                    playAgain = false;

                    int caseCounter = 0;

                    String jailOptionStr = "";
                    String roll = lib.text.get("JailRoll");
                    String pay = lib.text.get("JailPay");
                    String jailCard = lib.text.get("JailCard");

                    if(jailCaseNum > 0){
                        switch(jailCaseNum){
                            case (1):                                    //if the player chooses to roll for a pair
                                int[] diceRoll = dice.roll(testing);
                                System.out.println(diceRoll[0] +", "+ diceRoll[1]);

                                if (diceRoll[0] == diceRoll[1]) {
                                    p.setIsJailed(false);
                                    p.resetJailTurn();
                                    p.move(diceRoll[0] + diceRoll[1]);

                                    gui.updatePlayers(pLst);
                                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                    gui.updatePlayers(pLst);
                                    this.playerJailed = p.getIsJailed();
                                    p.kill();
                                } else if (p.getJailTurn() == 3) {          //if the player doesn't get a pair after 3 turns
                                    p.setIsJailed(false);               //then the player is forced to pay
                                    p.resetJailTurn();
                                    p.addBal(-1000);
                                    p.move(diceRoll[0] + diceRoll[1]);

                                    gui.updatePlayers(pLst);
                                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                    gui.updatePlayers(pLst);

                                    gui.showMessage(lib.text.get("PayedEscape"));

                                    this.playerJailed = p.getIsJailed();
                                    p.kill();
                                } else {
                                    p.addJailTurn();
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

                                this.playerJailed = p.getIsJailed();
                                p.kill();

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

                                this.playerJailed = p.getIsJailed();
                                p.kill();

                                break;

                            default:
                                throw new IllegalStateException("Unexpected value: " + jailOptionStr);

                        }
                    }else{
                        if (!p.getJailCard()) {          //if the player doesn't have a "Get out of jail" chance card
                            jailOptionStr = gui.getPlayerDropdown(lib.text.get("YourOptionsJail"), roll, pay);
                        } else {                           //if the player has a "Get out of jail" chance card
                            jailOptionStr = gui.getPlayerDropdown(lib.text.get("YourOptionsJail"), jailCard, roll, pay);
                        }
                        if (jailOptionStr == roll) {
                            caseCounter = 1;
                        } else if (jailOptionStr == pay) {
                            caseCounter = 2;
                        } else {
                            caseCounter = 3;
                        }

                        switch (caseCounter) {

                            case (1):                                    //if the player chooses to roll for a pair
                                int[] diceRoll = dice.roll(testing);
                                gui.showDiceOnBoard(diceRoll);

                                if (diceRoll[0] == diceRoll[1]) {
                                    p.setIsJailed(false);
                                    p.resetJailTurn();
                                    p.move(diceRoll[0] + diceRoll[1]);

                                    gui.updatePlayers(pLst);
                                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                    gui.updatePlayers(pLst);
                                } else if (p.getJailTurn() == 3) {          //if the player doesn't get a pair after 3 turns
                                    p.setIsJailed(false);               //then the player is forced to pay
                                    p.resetJailTurn();
                                    p.addBal(-1000);
                                    p.move(diceRoll[0] + diceRoll[1]);

                                    gui.updatePlayers(pLst);
                                    board.getBoard()[p.getFieldNumber()].landOnField(p, pLst, deck, board, gui, lib);
                                    gui.updatePlayers(pLst);

                                    gui.showMessage(lib.text.get("PayedEscape"));
                                } else {
                                    p.addJailTurn();
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
            }
        } while (playAgain);
    }

    public void setTesting() {
        testing = !testing;
        deck = new ChanceDeck(lib, testing, -1);
        gui.setTesting(testing);
    }

    private void propertyMangement(Player player, GameBoard board) throws InterruptedException {

        // Choose a field
        // Sell house or choose a new field
        // Roll
        String playerNextStep;
        int propertyIndex = 0;

        boolean chooseField = true;

        do {
            OwnableField[] playersFields = player.getPlayersFields(board.getOwnableBoard());
            gui.updateBoard(playersFields,pLst);
            gui.updatePlayers(pLst);

            if (chooseField) {
                String fieldNames[] = new String[playersFields.length];

                for (int i = 0; i < playersFields.length; i++) {
                    fieldNames[i] = playersFields[i].getName();
                }

                gui.updateBoard(board.getOwnableBoard(), pLst);

                String selectedFieldName = gui.getPlayerDropdown(lib.text.get("ChooseAField"), fieldNames);
                propertyIndex = getOwnableFieldIndex(selectedFieldName, playersFields);
                chooseField = false;
            }

            System.out.println(playersFields[propertyIndex].getMortgage());
            if(!playersFields[propertyIndex].getMortgage()) {
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
                        playersFields[propertyIndex].sellHouseAndHotel(player,playersFields);
                        chooseField = true;
                    }

                }
                // Sælger et hus
                else {
                    playersFields[propertyIndex].sellHouseAndHotel(player,playersFields);
                        /*playersFields[propertyIndex].minusOneLevel();
                        player.addBal(playersFields[propertyIndex].getHousePrice());*/
                }
            }
            //Mortage a property
            else if(playerNextStep.equals(lib.text.get("MortgageProp"))){
                playersFields[propertyIndex].setMortgage(true);
                int price = playersFields[propertyIndex].getPrice();
                double input = price * 0.5;
                int money = (int)input;
                player.addBal(money);
                int boardIndex = getFieldIndex(playersFields[propertyIndex].getName(), board.getBoard());
                gui.getGui().getFields()[boardIndex].setForeGroundColor(Color.GRAY);
            }
            //Reopen properties
            else if(playerNextStep.equals(lib.text.get("ReopenProp"))){
                playersFields[propertyIndex].setMortgage(false);
                int price = playersFields[propertyIndex].getPrice();
                double input = price*0.55;
                int money = (int)input;
                player.addBal(-money);
                int boardIndex = getFieldIndex(playersFields[propertyIndex].getName(), board.getBoard());
                gui.getGui().getFields()[boardIndex].setForeGroundColor(Color.BLACK);
            }
            // choose new field
            else if (playerNextStep.equals(lib.text.get("ChooseNewField"))) {
                chooseField = true;
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

    public void managementStream(Player p, GameBoard board, String end) throws InterruptedException {
        while(true) {
            String inputBtn = gui.getPlayerBtn(String.format(lib.text.get("MessagePM"), p.getName()), lib.text.get("PM"), lib.text.get(end), lib.text.get("GiveUp"));

            if (inputBtn.equals(lib.text.get("PM"))) {
                if (p.getPlayersFields(board.getOwnableBoard()).length < 1){
                    gui.showMessage(lib.text.get("NoFields"));
                }
                else {
                    propertyMangement(p, board);
                }
            }
            while (inputBtn.equals(lib.text.get("GiveUp"))) {
                String answer = gui.getPlayerBtn(lib.text.get("Sure"), lib.text.get("No"), lib.text.get("Yes"));
                if(answer.equals(lib.text.get("Yes"))){
                    p.kill();
                    break;
                }
                else{
                    break;
                }
            }
            if (inputBtn.equals(lib.text.get(end))){
                break;
            }
            if (!p.getAlive()){
                gui.showMessage(lib.text.get("PlayerGiveUp"));
                gui.updateBoard(board.getOwnableBoard(), pLst);
                gui.updatePlayers(pLst);
                break;
            }
        }
    }
}
