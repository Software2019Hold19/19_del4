package GUI;
import Main.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;
import java.util.concurrent.TimeUnit;

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
    private Boolean testing = false;

    public PlayerObserver(Player[] pLst){
        super();

        guiPlayerList = new GUI_Player[pLst.length];

        for (int i = 0; i < pLst.length; i++) {
            guiPlayerList[i] = new GUI_Player(pLst[i].getName(), pLst[i].getBal());
            guiPlayerList[i].getCar().setPrimaryColor(colorLst[i]);
        }
    }

   // @Override
    public void update(GUI gui, Player[] pLst) throws InterruptedException {
        for (int i = 0; i < pLst.length; i++){
            guiPlayerList[i].setBalance(pLst[i].getBal());
        }
        updatePlayerPos(gui, pLst);
    }

    public void updatePlayerPos(GUI gui, Player[] pLst) throws InterruptedException {
        for (GUI_Field field : gui.getFields()) {
            field.removeAllCars();
        }
        Boolean needsUpdate = false;
        for (int i = 0; i < pLst.length; i++){
            if (pLst[i].getAlive()) {
                if (pLst[i].getFieldNumber() == pLst[i].getOldFieldNumber()) {
                    gui.getFields()[pLst[i].getFieldNumber()].setCar(guiPlayerList[i], true);
                } else {
                    needsUpdate = true;
                    pLst[i].step();
                    gui.getFields()[pLst[i].getOldFieldNumber()].setCar(guiPlayerList[i], true);
                }
            }
        }
        if (needsUpdate) {
            if (!testing) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            updatePlayerPos(gui, pLst);
        }
    }

    public GUI_Player[] getGuiPlayerList() {
        return guiPlayerList;
    }

    public void setTesting(Boolean test) {
        testing = test;
    }

}
