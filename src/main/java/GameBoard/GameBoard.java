package GameBoard;

import Main.Translator;

public class GameBoard {
    private Field[] board; 
    Translator lib;
    private String[] fieldLst = {
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

    public GameBoard(Translator _lib){
        this.lib = _lib;
        board = new Field[24];
        initGame();
    }

    public void boardUpdate(Translator _lib){
        this.lib = _lib;
        initGame();
    }

    public String toString(){
        String res = "";
        for (Field field : getBoard()){
            System.out.println(field.toString());
            res += field.toString() + "\n";
        }
        return res;
    }

    public void initGame(){


        //initiates all fields
        // form: fieldname from translator key, subName from translator key, description from translator key, type, price, color
        // key is split by ":" to a String[]. index 0 = name, index 1 = subName, index 2 = desc
        String[] txtlst = lib.text.get("Start").split(":");
        board[0] = new StartField(txtlst[0], txtlst[1], txtlst[2],"start");

        txtlst = lib.text.get("Brown1").split(":");
        board[1] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "brown", fieldLst[1]);

        txtlst = lib.text.get("Brown2").split(":");
        board[2] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "brown", fieldLst[2]);

        txtlst = lib.text.get("Chance").split(":");
        board[3] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Lightblue1").split(":");
        board[4] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "cyan", fieldLst[4]);

        txtlst = lib.text.get("Lightblue2").split(":");
        board[5] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "cyan", fieldLst[5]);

        txtlst = lib.text.get("Visiting").split(":");
        board[6] = new VisitingField(txtlst[0], txtlst[1], txtlst[2], "jail");

        txtlst = lib.text.get("Purple1").split(":");
        board[7] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "magenta", fieldLst[7]);

        txtlst = lib.text.get("Purple2").split(":");
        board[8] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "magenta", fieldLst[8]);

        txtlst = lib.text.get("Chance").split(":");
        board[9] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Visiting").split(":");
        board[10] = new VisitingField(txtlst[0], txtlst[1], txtlst[2], "jail");

        txtlst = lib.text.get("Green2").split(":");
        board[11] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[11]);

        txtlst = lib.text.get("Brewery1").split(":");
        board[12] = new BrewerryField(txtlst[0], txtlst[1], txtlst[2], "Brewery", txtlst[3]);

        txtlst = lib.text.get("Green2").split(":");
        board[13] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[13]);

        txtlst = lib.text.get("Green3").split(":");
        board[14] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[14]);

        txtlst = lib.text.get("Ferry2").split(":");
        board[15] = new FerryField(txtlst[0], txtlst[1], txtlst[2], "ferry", txtlst[3]);

        txtlst = lib.text.get("Darkgray1").split(":");
        board[16] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgray", fieldLst[16]);

        txtlst = lib.text.get("Chance").split(":");
        board[17] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Darkgray2").split(":");
        board[18] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgray", fieldLst[16]);

        txtlst = lib.text.get("Darkgray3").split(":");
        board[19] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgray", fieldLst[16]);

        txtlst = lib.text.get("Green2").split(":");
        board[20] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 4, "green", fieldLst[20]);

        txtlst = lib.text.get("Chance").split(":");
        board[21] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Darkblue1").split(":");
        board[22] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 5, "blue", fieldLst[22]);

        txtlst = lib.text.get("Darkblue2").split(":");
        board[23] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 5, "blue", fieldLst[23]);
        txtlst = lib.text.get("Brown1").split(":");

        board[24] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "brown", fieldLst[1]);

        txtlst = lib.text.get("Brown2").split(":");
        board[25] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "brown", fieldLst[2]);

        txtlst = lib.text.get("Chance").split(":");
        board[26] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Lightblue1").split(":");
        board[27] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "cyan", fieldLst[4]);

        txtlst = lib.text.get("Lightblue2").split(":");
        board[28] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 1, "cyan", fieldLst[5]);

        txtlst = lib.text.get("Visiting").split(":");
        board[29] = new VisitingField(txtlst[0], txtlst[1], txtlst[2], "jail");

        txtlst = lib.text.get("Purple1").split(":");
        board[30] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "magenta", fieldLst[7]);

        txtlst = lib.text.get("Purple2").split(":");
        board[31] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "magenta", fieldLst[8]);

        txtlst = lib.text.get("Chance").split(":");
        board[32] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Orange1").split(":");
        board[33] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "orange", fieldLst[10]);

        txtlst = lib.text.get("Orange2").split(":");
        board[34] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 2, "orange", fieldLst[11]);

        txtlst = lib.text.get("FreeParking").split(":");
        board[35] = new VisitingField(txtlst[0], txtlst[1], txtlst[2],"visit");

        txtlst = lib.text.get("Red1").split(":");
        board[36] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 3, "red", fieldLst[13]);

        txtlst = lib.text.get("Red2").split(":");
        board[37] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 3, "red", fieldLst[14]);

        txtlst = lib.text.get("Chance").split(":");
        board[38] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Yellow1").split(":");
        board[39] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", 3, "yellow", fieldLst[16]);

    }

    public Field[] getBoard() {
        return board;
    }

    public OwnableField[] getOwnableFields() {
        return new OwnableField[12]; // TODO: have to chance!!!
    }
}
