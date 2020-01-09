package GUI;

import GameBoard.Field;
import Main.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import java.awt.*;
import java.util.HashMap;

public class BoardObserver extends Observer {

    public BoardObserver() {

    }

    public void ownerUpdate(GUI_Field[] guiFields, Field[] fields, HashMap<String, GUI_Street> streets, GUI_Player[] guiPLst, Player[] pLst){
        for (int i = 0; i < fields.length; i++){
            if (fields[i].getInfo()[3].equals("street")){
                String owner = fields[i].getInfo()[6];
                String key = fields[i].getInfo()[7];
                // find owner and see if ded - if ded remove owner
                for(Player p : pLst){
                    if(owner.equals(p.getName()) && !p.getAlive()){
                        owner = "";
                        fields[i].setOwner("");
                        streets.get(key).setBorder(Color.BLACK);
                    }
                }
                if (!owner.equals("")){
                    guiFields[i].setDescription(fields[i].getDesc() + "\nOwner: " + owner);
                    for (GUI_Player guiP : guiPLst){
                        if (guiP.getName().equals(owner)){
                            Color color = guiP.getCar().getPrimaryColor();
                            streets.get(key).setBorder(color); //test
                        }
                    }
                }
            }
        }
    }
}
