package GUI;

import GameBoard.GameBoard;
import GameBoard.OwnableField;
import Main.Player;
import Main.Translator;
//Side Fields
import gui_fields.*;
import gui_main.GUI;


//Corner Fields


public class GUIController {

    private GameBoard board;
    public GUI gui;
    private PlayerObserver pObs;
    private BoardObserver bObs = new BoardObserver();
    private Translator lib;
    private FieldFactory fieldFac = new FieldFactory();
    private Boolean testing = false;
    private int testNames = 0;
    private int testCount = -1;

    public GUIController(Translator _lib, GameBoard _board) {
        lib = _lib;
        board = _board;
        gui = new GUI(boardSetup(board));
    }

    public void showDiceOnBoard(int[] roll){
        /*if (testing) {
            gui.showMessage("Next turn");
        }*/

        
        gui.setDice(roll[0], roll[1]);
    }


    public void showMessage(String txt){
        if (!testing) {
            this.gui.showMessage(txt);
        }
    }

    public void displayChanceCard(String txt){
        if (!testing) {
            this.gui.displayChanceCard(txt);
        }
    }

    public String getPlayerDropdown(java.lang.String msg, java.lang.String... buttons)
    {
        if (testing){
            return buttons[testCount++ % buttons.length];
        }
        return gui.getUserSelection(msg, buttons);
    }

    public String getPlayerBtn(String msg, java.lang.String... buttons){
        if (testing) {
            return buttons[buttons.length - 1];
        }
        else {
            return gui.getUserButtonPressed(msg, buttons);
        }
    }

    public int getPlayerInt(String msg, int price, int bal){
        if (testing) {
            return 0;
        } else {
            return gui.getUserInteger(msg, 50, bal - price);
        }
    }

    public String getUserString(String msg) {
        if (testing){
            return String.valueOf(testNames++);
        }
        return gui.getUserString(msg);
    }

    public void addPlayers(Player[] playerList){
        pObs = new PlayerObserver(playerList);
        pObs.setTesting(testing);

        for (GUI_Player p : pObs.getGuiPlayerList()) {
            gui.addPlayer(p);
            gui.getFields()[0].setCar(p, true);
        }
        
    }

    private GUI_Field[] boardSetup(GameBoard board){
        return fieldFac.boardSetup(board);
    }

    public void updatePlayers(Player[] pLst) throws InterruptedException {
        pObs.update(gui, pLst);
    }

    public void updateBoard(OwnableField[] fLst, Player[] pLst){
        bObs.ownerUpdate(gui.getFields(), fLst, fieldFac.getOwnable(), pObs.getGuiPlayerList(), pLst, fieldFac.getStreets(), board);

    }

    public GUI getGui() {
        return gui;
    }

    public void updateLanguage(Translator _lib){
        this.lib = _lib;
        String[] fieldLst = {
                "Start",
                "Lightblue1",
                "Chance",
                "Lightblue2",
                "Tax1",
                "Ferry1",
                "Orange1",
                "Chance",
                "Orange2", "Orange3",
                "Visiting",
                "Green1",
                "Brewery1",
                "Green2", "Green3",
                "Ferry2",
                "Darkgrey1",
                "Chance",
                "Darkgrey2", "Darkgrey3",
                "Freeparking",
                "Red1",
                "Chance",
                "Red2", "Red3",
                "Ferry3",
                "Lightgrey1", "Lightgrey2",
                "Brewery2",
                "Lightgrey3",
                "Gotojail",
                "Yellow1", "Yellow2",
                "Chance",
                "Yellow3",
                "Ferry4",
                "Chance",
                "Brown1",
                "Tax2",
                "Brown2"
        };

        int i = 0;
        for (GUI_Field field : gui.getFields()){
            String[] tmpLst = lib.text.get(fieldLst[i]).split(": ");
            if (!tmpLst[0].equals("?")) {
                field.setTitle(tmpLst[0]);
            }
            field.setSubText(tmpLst[1]);
            field.setDescription(tmpLst[2]);
            i++;
        }



    }

    public void setTesting(Boolean test){
        testing = test;
        try {
            pObs.setTesting(testing);
        } catch (NullPointerException e) {
            System.out.println("start of testing");
        }
    }

}
