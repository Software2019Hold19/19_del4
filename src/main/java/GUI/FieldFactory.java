package GUI;

import GameBoard.GameBoard;
import GameBoard.Field;
import gui_fields.*;

import java.awt.*;
import java.util.HashMap;

public class FieldFactory {

    private HashMap<String, GUI_Street> streets = new HashMap<String, GUI_Street>();

    public FieldFactory() {
    }

    public HashMap<String, GUI_Street> getStreets() {
        return streets;
    }

    public GUI_Field[] boardSetup(GameBoard board){
        GUI_Field[] guiFields = new GUI_Field[24];
        Field[] fields = board.getBoard();
        int i = 0;
        for (Field field : fields){
            String type = field.getType();
            switch(type){
                case ("start"):
                    guiFields[i++] = new GUI_Start(field.getName(), field.getSubName(), field.getDesc(), Color.RED, Color.BLACK);
                    break;

                case ("street"):
                    String[] tmpLst = field.getInfo();
                    Color color = Color.BLACK;
                    switch (tmpLst[5]){
                        case("brown"):
                            color = new Color(127, 76, 25);
                            break;

                        case("cyan"):
                            color = Color.CYAN;
                            break;

                        case("magenta"):
                            color = Color.MAGENTA;
                            break;

                        case("orange"):
                            color = Color.ORANGE;
                            break;

                        case("red"):
                            color = Color.RED;
                            break;

                        case("yellow"):
                            color = Color.YELLOW;
                            break;

                        case("green"):
                            color = Color.GREEN;
                            break;

                        case("blue"):
                            color = Color.BLUE;
                            break;

                    }
                    GUI_Street street = new GUI_Street(field.getName(), field.getSubName(), field.getDesc(), tmpLst[4], color, Color.BLACK);
                    streets.put(tmpLst[7], street);
                    guiFields[i++] = streets.get(tmpLst[7]);
                    break;

                case ("visit"):
                    guiFields[i++] = new GUI_Refuge("default", field.getName(), field.getSubName(), field.getDesc(), Color.WHITE, Color.BLACK);
                    break;

                case ("jail"):
                    guiFields[i++] = new GUI_Jail("default", field.getName(), field.getSubName(), field.getDesc(), new Color(125, 125, 125), Color.BLACK);
                    break;

                case ("chance"):
                    guiFields[i++] = new GUI_Chance("?", field.getSubName(), field.getDesc(), new Color(204, 204, 204), Color.BLACK);
                    break;

            }
        }
        return guiFields;
    }

}
