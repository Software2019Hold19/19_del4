package GameBoard;

import Main.Translator;

public class GameBoard {
    private Field[] board; 
    Translator lib;
    private String[] fieldLst = {
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
            "Darkgray1",
            "Chance",
            "Darkgray2", "Darkgray3",
            "Freeparking",
            "Red1",
            "Chance",
            "Red2", "Red3",
            "Ferry3",
            "Lightgray1", "Lightgray2",
            "Brewery2",
            "Lightgray3",
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

    public GameBoard(Translator _lib){
        this.lib = _lib;
        board = new Field[40];
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

        txtlst = lib.text.get("Lightblue1").split(":");
        board[1] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightblue", fieldLst[1]);

        txtlst = lib.text.get("Chance").split(":");
        board[2] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Lightblue2").split(":");
        board[3] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightblue", fieldLst[3]);

        txtlst = lib.text.get("Tax1").split(":");
        board[4] = new TaxField(txtlst[0], txtlst[1], txtlst[2],"tax");

        txtlst = lib.text.get("Ferry1").split(":");
        board[5] = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3]);

        txtlst = lib.text.get("Orange1").split(":");
        board[6] = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3], "orange", fieldLst[6]);

        txtlst = lib.text.get("Chance").split(":");
        board[7] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Orange2").split(":");
        board[8] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "orange", fieldLst[8]);

        txtlst = lib.text.get("Orange3").split(":");
        board[9] = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3], "orange", fieldLst[9]);

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
        board[18] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgray", fieldLst[18);

        txtlst = lib.text.get("Darkgray3").split(":");
        board[19] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgray", fieldLst[19]);

        txtlst = lib.text.get("Freeparking").split(":");
        board[20] = new VisitingField(txtlst[0], txtlst[1], txtlst[2],"visit");

        txtlst = lib.text.get("Red1").split(":");
        board[21] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[21]);

        txtlst = lib.text.get("Chance").split(":");
        board[22] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Red2").split(":");
        board[23] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[23]);

        txtlst = lib.text.get("Red3").split(":");
        board[24] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[24]);

        txtlst = lib.text.get("Ferry3").split(":");
        board[25] = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", fieldLst[25]);

        txtlst = lib.text.get("Lightgray1").split(":");
        board[26] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgray", fieldLst[26]);

        txtlst = lib.text.get("Lightgray2").split(":");
        board[27] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgray", fieldLst[27]);

        txtlst = lib.text.get("Brewery2").split(":");
        board[28] = new BrewerryField(txtlst[0], txtlst[1], txtlst[2],"brewery", txtlst[3]);

        txtlst = lib.text.get("Lightgray3").split(":");
        board[29] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgray", fieldLst[29]);

        txtlst = lib.text.get("Gotojail").split(":");
        board[30] = new VisitingField(txtlst[0], txtlst[1], txtlst[2],"visit");

        txtlst = lib.text.get("Yellow1").split(":");
        board[31] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "yellow", fieldLst[31]);

        txtlst = lib.text.get("Yellow2").split(":");
        board[32] = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3],"yellow",fieldLst[32]);

        txtlst = lib.text.get("Chance").split(":");
        board[33] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Yellow3").split(":");
        board[34] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "yellow", fieldLst[34]);

        txtlst = lib.text.get("Ferry4").split(":");
        board[35] = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3]);

        txtlst = lib.text.get("Chance").split(":");
        board[36] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Brown1").split(":");
        board[37] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "brown", fieldLst[37]);

        txtlst = lib.text.get("Tax2").split(":");
        board[38] = new TaxField(txtlst[0], txtlst[1], txtlst[2],"tax");

        txtlst = lib.text.get("Brown2").split(":");
        board[39] = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "brown", fieldLst[39]);

    }

    public Field[] getBoard() {
        return board;
    }

    public OwnableField[] getOwnableFields() {
        return new OwnableField[12]; // TODO: have to chance!!!
    }
}
