package GUI;

import GameBoard.GameBoard;
import GameBoard.Field;
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

    public GUIController(Translator _lib, GameBoard _board) {
        lib = _lib;
        board = _board;
        //GUI_Field[];
        gui = new GUI(boardSetup(board));
    }

    public void showDiceOnBoard(int roll){
        gui.setDie(roll);
    }


    public void showMessage(String txt){
        this.gui.showMessage(txt);
    }

    public String getPlayerDropbown(java.lang.String msg, java.lang.String... buttons)
    {
        return gui.getUserSelection(msg,buttons);
    }

    public String getUserString(String msg) {
        return gui.getUserString(msg);
    }

    public void addPlayers(Player[] playerList){
        pObs = new PlayerObserver(playerList);

        for (GUI_Player p : pObs.getGuiPlayerList()) {
            gui.addPlayer(p);
            gui.getFields()[0].setCar(p, true);
        }
        
    }

    private GUI_Field[] boardSetup(GameBoard board){
        return fieldFac.boardSetup(board);
    }

    public void updatePlayers(Player[] pLst){
        pObs.update(gui, pLst);
    }

    public void updateBoard(Field[] fLst){
        bObs.ownerUpdate(gui.getFields(), fLst, fieldFac.getStreets(), pObs.getGuiPlayerList());
    }

    public GUI getGui() {
        return gui;
    }

    public void updateLanguage(Translator _lib){
        this.lib = _lib;
        String[] fieldLst = {
                "Start",
                "Brown1","Brown2",
                "Chance",
                "Lightblue1", "Lightblue2",
                "Visiting",
                "Purple1", "Purple2",
                "Chance",
                "Orange1", "Orange2",
                "FreeParking",
                "Red1", "Red2",
                "Chance",
                "Yellow1", "Yellow2",
                "GoToJail",
                "Green1", "Green2",
                "Chance",
                "Darkblue1", "Darkblue2",
        };

        int i = 0;
        for (GUI_Field field : gui.getFields()){
            String[] tmpLst = lib.text.get(fieldLst[i]).split(":");
            if (!tmpLst[0].equals("?")) {
                field.setTitle(tmpLst[0]);
            }
            field.setSubText(tmpLst[1]);
            field.setDescription(tmpLst[2]);
            i++;
        }



    }

}
