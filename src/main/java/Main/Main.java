package Main;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller co = new Controller();
        //System.out.println(co.lib.text.get("Start").split(":"));

        co.startGame();

/*        for (GUI_Field field : gui.getFields())
            System.out.println(field.toString());*/
    }
}