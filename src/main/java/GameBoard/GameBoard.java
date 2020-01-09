package GameBoard;

import Main.Translator;

public class GameBoard {
    private Field[] board;
    private OwnableField[] ownableBoard;
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
            "Darkgrey1",
            "Chance",
            "Darkgrey2", "Darkgrey3",
            "Freeparking",
            "Red1",
            "Chance",
            "Red2", "Red3",
            "Ferry3",
            "Lightgrey1", "Lightgrey2",
            "Brewery2",
            "Lightgrey3",
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
        ownableBoard = new OwnableField[28];
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
        String[] txtlst = lib.text.get("Start").split(": ");
        board[0] = new StartField(txtlst[0], txtlst[1], txtlst[2],"start");

        txtlst = lib.text.get("Lightblue1").split(": ");
        StreetField tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightblue", fieldLst[1]);
        ownableBoard[0] = tmpField;
        board[1] = tmpField;

        txtlst = lib.text.get("Chance").split(": ");
        board[2] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Lightblue2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightblue", fieldLst[3]);
        ownableBoard[1] = tmpField;
        board[3] = tmpField;

        txtlst = lib.text.get("Tax1").split(": ");
        board[4] = new TaxField(txtlst[0], txtlst[1], txtlst[2],"tax");

        txtlst = lib.text.get("Ferry1").split(": ");
        FerryField tmpFieldF = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3], fieldLst[5]);
        ownableBoard[2] = tmpFieldF;
        board[5] = tmpFieldF;

        txtlst = lib.text.get("Orange1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3], "orange", fieldLst[6]);
        ownableBoard[3] = tmpField;
        board[6] = tmpField;

        txtlst = lib.text.get("Chance").split(": ");
        board[7] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Orange2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3], "orange", fieldLst[8]);
        ownableBoard[4] = tmpField;
        board[8] = tmpField;

        txtlst = lib.text.get("Orange3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2], "street", txtlst[3], "orange", fieldLst[9]);
        ownableBoard[5] = tmpField;
        board[9] = tmpField;

        txtlst = lib.text.get("Visiting").split(": ");
        board[10] = new VisitingField(txtlst[0], txtlst[1], txtlst[2], "jail");

        txtlst = lib.text.get("Green1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[11]);
        ownableBoard[6] = tmpField;
        board[11] = tmpField;

        txtlst = lib.text.get("Brewery1").split(": ");
        BreweryField tmpFieldB = new BreweryField(txtlst[0], txtlst[1], txtlst[2],"brewery", txtlst[3], fieldLst[12]);
        ownableBoard[7] = tmpFieldB;
        board[12] = tmpFieldB;

        txtlst = lib.text.get("Green2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[13]);
        ownableBoard[8] = tmpField;
        board[13] = tmpField;

        txtlst = lib.text.get("Green3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "green", fieldLst[14]);
        ownableBoard[9] = tmpField;
        board[14] = tmpField;

        txtlst = lib.text.get("Ferry2").split(": ");
        tmpFieldF = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3], fieldLst[15]);
        ownableBoard[10] = tmpFieldF;
        board[15] = tmpFieldF;

        txtlst = lib.text.get("Darkgrey1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgrey", fieldLst[16]);
        ownableBoard[11] = tmpField;
        board[16] = tmpField;

        txtlst = lib.text.get("Chance").split(": ");
        board[17] = new ChanceField(txtlst[0], txtlst[1], txtlst[2], "chance");

        txtlst = lib.text.get("Darkgrey2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgrey", fieldLst[18]);
        ownableBoard[12] = tmpField;
        board[18] = tmpField;

        txtlst = lib.text.get("Darkgrey3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "darkgrey", fieldLst[19]);
        ownableBoard[13] = tmpField;
        board[19] = tmpField;

        txtlst = lib.text.get("Freeparking").split(": ");
        board[20] = new VisitingField(txtlst[0], txtlst[1], txtlst[2],"visit");

        txtlst = lib.text.get("Red1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[21]);
        ownableBoard[14] = tmpField;
        board[21] = tmpField;

        txtlst = lib.text.get("Chance").split(": ");
        board[22] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Red2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[23]);
        ownableBoard[15] = tmpField;
        board[23] = tmpField;

        txtlst = lib.text.get("Red3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "red", fieldLst[24]);
        ownableBoard[16] = tmpField;
        board[24] = tmpField;

        txtlst = lib.text.get("Ferry3").split(": ");
        tmpFieldF = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3], fieldLst[25]);
        ownableBoard[17] = tmpFieldF;
        board[25] = tmpFieldF;

        txtlst = lib.text.get("Lightgrey1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgrey", fieldLst[26]);
        ownableBoard[18] = tmpField;
        board[26] = tmpField;

        txtlst = lib.text.get("Lightgrey2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgrey", fieldLst[27]);
        ownableBoard[19] = tmpField;
        board[27] = tmpField;

        txtlst = lib.text.get("Brewery2").split(": ");
        tmpFieldB = new BreweryField(txtlst[0], txtlst[1], txtlst[2],"brewery", txtlst[3], fieldLst[28]);
        ownableBoard[20] = tmpFieldB;
        board[28] = tmpFieldB;

        txtlst = lib.text.get("Lightgrey3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "lightgrey", fieldLst[29]);
        ownableBoard[21] = tmpField;
        board[29] = tmpField;

        txtlst = lib.text.get("Gotojail").split(": ");
        board[30] = new GoToJailField(txtlst[0], txtlst[1], txtlst[2],"jail");

        txtlst = lib.text.get("Yellow1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "yellow", fieldLst[31]);
        ownableBoard[22] = tmpField;
        board[31] = tmpField;

        txtlst = lib.text.get("Yellow2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "yellow", fieldLst[32]);
        ownableBoard[23] = tmpField;
        board[32] = tmpField;

        txtlst = lib.text.get("Chance").split(": ");
        board[33] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Yellow3").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "yellow", fieldLst[34]);
        ownableBoard[24] = tmpField;
        board[34] = tmpField;

        txtlst = lib.text.get("Ferry4").split(": ");
        tmpFieldF = new FerryField(txtlst[0], txtlst[1], txtlst[2],"ferry", txtlst[3], fieldLst[35]);
        ownableBoard[25] = tmpFieldF;
        board[35] = tmpFieldF;

        txtlst = lib.text.get("Chance").split(": ");
        board[36] = new ChanceField(txtlst[0], txtlst[1], txtlst[2],"chance");

        txtlst = lib.text.get("Brown1").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "brown", fieldLst[37]);
        ownableBoard[26] = tmpField;
        board[37] = tmpField;

        txtlst = lib.text.get("Tax2").split(": ");
        board[38] = new TaxField(txtlst[0], txtlst[1], txtlst[2],"tax");

        txtlst = lib.text.get("Brown2").split(": ");
        tmpField = new StreetField(txtlst[0], txtlst[1], txtlst[2],"street", txtlst[3], "brown", fieldLst[39]);
        ownableBoard[27] = tmpField;
        board[39] = tmpField;


        //Test
/*
        for (int i = 0; i < ownableBoard.length; i++) {
            ownableBoard[i].setOwner("Oli");
        }

*/
    }

    public Field[] getBoard() {
        return board;
    }

    public OwnableField[] getOwnableBoard() {
        return ownableBoard;
    }
}
