package GUI;

import GameBoard.Field;
import GameBoard.OwnableField;
import Main.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Ownable;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import java.awt.*;
import java.util.HashMap;

public class BoardObserver extends Observer {

    public BoardObserver() {

    }

    public void ownerUpdate(GUI_Field[] guiFields, OwnableField[] fields, HashMap<String, GUI_Ownable> ownable, GUI_Player[] guiPLst, Player[] pLst, HashMap<String, GUI_Street> streets){
        for (int i = 0; i < fields.length; i++){
            if (fields[i].getType().equals("street") || fields[i].getType().equals("brewery") || fields[i].getType().equals("ferry")){
                String owner = fields[i].getOwner();
                String key = fields[i].getKey();
                // find owner and see if ded - if ded remove owner
                for(Player p : pLst){
                    if(owner.equals(p.getName()) && !p.getAlive()){
                        owner = "";
                        fields[i].setOwner("");
                        ownable.get(key).setBorder(Color.BLACK);
                        if (fields[i].getType().equals("street")) {
                            streets.get(key).setHotel(false);
                        }
                    }
                }
                if (!owner.equals("")){
                    guiFields[i].setDescription(fields[i].getDesc() + "\nOwner: " + owner);
                    for (GUI_Player guiP : guiPLst){
                        if (guiP.getName().equals(owner)){
                            Color color = guiP.getCar().getPrimaryColor();
                            ownable.get(key).setBorder(color); //test
                        }
                    }
                }
            }
        }
    }
}
