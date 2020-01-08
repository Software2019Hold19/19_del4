package GUI;
import Main.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.*;

public class PlayerObserver extends Observer{
    //private Player player = new Player("test");
    private GUI_Player[] guiPlayerList;
    Color[] colorLst = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA
    };

    public PlayerObserver(Player[] pLst){
        super();

        guiPlayerList = new GUI_Player[pLst.length];

        for (int i = 0; i < pLst.length; i++) {
            guiPlayerList[i] = new GUI_Player(pLst[i].getName(), pLst[i].getBal());
            guiPlayerList[i].getCar().setPrimaryColor(colorLst[i]);
        }
    }

   // @Override
    public void update(GUI gui, Player[] pLst) {
        for (int i = 0; i < pLst.length; i++){
            guiPlayerList[i].setBalance(pLst[i].getBal());
        }
        updatePlayerPos(gui, pLst);
    }

    public void updatePlayerPos(GUI gui, Player[] pLst){
        for (GUI_Field field : gui.getFields()) {
            field.removeAllCars();
        }
        for (int i = 0; i < pLst.length; i++){
           gui.getFields()[pLst[i].getFieldNumber()].setCar(guiPlayerList[i], true);
        }
    }

    public GUI_Player[] getGuiPlayerList() {
        return guiPlayerList;
    }
}
