package GUI;

import GameBoard.GameBoard;
import GameBoard.Field;
import gui_fields.*;

import java.awt.*;
import java.util.HashMap;

public class FieldFactory {

    private HashMap<String, GUI_Ownable> ownable = new HashMap<String, GUI_Ownable>();
    private HashMap<String, GUI_Street> streets = new HashMap<String, GUI_Street>();

    public FieldFactory() {
    }

    public HashMap<String, GUI_Ownable> getOwnable() {
        return ownable;
    }

    public HashMap<String, GUI_Street> getStreets() {
        return streets;
    }

    public GUI_Field[] boardSetup(GameBoard board){
        GUI_Field[] guiFields = new GUI_Field[40];
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
                        case("lightgrey"):
                            color = new Color(224, 224, 224);
                            break;

                        case("lightblue"):
                            color = new Color(31, 236, 255);
                            break;

                        case("darkgrey"):
                            color = new Color(117, 117, 117);
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

                        case("brown"):
                            color = new Color(127, 76, 25);
                            break;

                        default:
                            color = Color.BLACK;
                            break;

                    }
                    GUI_Street street = new GUI_Street(field.getName(), field.getSubName(), field.getDesc(), tmpLst[4], color, Color.BLACK);
                    ownable.put(field.getKey(), street);
                    streets.put(field.getKey(), street);
                    guiFields[i++] = ownable.get(tmpLst[7]);
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

                case ("brewery"):
                    GUI_Brewery brew = new GUI_Brewery("default", field.getName(), field.getSubName(), field.getDesc(), Integer.toString(field.getRent()), Color.BLACK, Color.WHITE);
                    ownable.put(field.getKey(), brew);
                    guiFields[i++] = brew;
                    break;

                case ("ferry"):
                    GUI_Shipping ship = new GUI_Shipping("default", field.getName(), field.getSubName(), field.getDesc(), Integer.toString(field.getRent()),Color.WHITE, Color.BLACK);
                    ownable.put(field.getKey(), ship);
                    guiFields[i++] = ship;
                    break;

                case ("tax"):
                    guiFields[i++] = new GUI_Tax(field.getName(), field.getSubName(), field.getDesc(), Color.WHITE, Color.BLUE);
                    break;
            }
        }
        for (String key : ownable.keySet()) {
            ownable.get(key).setBorder(Color.BLACK);
        }
        return guiFields;
    }

}
