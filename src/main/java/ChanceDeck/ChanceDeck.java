package ChanceDeck;

import Main.Player;
import Main.Translator;
import GameBoard.OwnableField;

/**
 * ChanceDeck
 * Controls the deck and the cards in it.
 */
public class ChanceDeck {

    ChanceCard deck[] = new ChanceCard[18];
    int cardCount = 0;

    public ChanceDeck(Translator lib, Boolean testing) {

        deck[0] = new ChoiceMoveChanceCard(lib.text.get("ChanceCTxt3"), true);
        deck[1] = new ChoiceMoveChanceCard(lib.text.get("ChanceCTxt4"), false);
        deck[2] = new BankChanceCard(lib.text.get("ChanceCTxt10"), 1000);
        deck[3] = new BankChanceCard(lib.text.get("ChanceCTxt2"), -2000);
        deck[4] = new SpecifikMoveChanceCard(lib.text.get("ChanceCTxt5"), 0);
        deck[5] = new SpecifikMoveChanceCard(lib.text.get("ChanceCTxt6"), 39);
        deck[6] = new JailChanceCard(lib.text.get("LandOnGoToJail"));
        deck[7] = new BankChanceCard(lib.text.get("ChanceCTxt1"),-2000);
        deck[8] = new BankChanceCard(lib.text.get("ChanceCTxt7"),-200);
        deck[9] = new GetOutOfJailFreeCard(lib.text.get("ChanceCTxt8"));
        deck[10] = new SpecifikMoveChanceCard(lib.text.get("ChanceCTxt9"),24);
        deck[11] = new PlayerMoveChanceCard(lib.text.get("ChanceCTxt11"));
        deck[12] = new BankChanceCard(lib.text.get("ChanceCTxt12"), 40000);
        deck[13] = new BankChanceCard(lib.text.get("ChanceCTxt13"),-400);
        deck[14] = new BankChanceCard(lib.text.get("ChanceCTxt14"),4000);
        deck[15] = new TaxChanceCard(lib.text.get("ChanceCTxt15"),500, 2500);
        deck[16] = new TaxChanceCard(lib.text.get("ChanceCTxt16"),1000, 2500);
        deck[17] = new SpecifikMoveChanceCard(lib.text.get("ChanceCTxt17"),15);


        if (!testing) {
            shuffleDeck();
        }
    }

    public void shuffleDeck(){
        /* shuffles the deck of chancecards
        - creates copy of deck (cardlist) and makes boolean array in same size.
        - each index in the deck is filled with a random card from cardlist
        - when a card is grabbed from the list it is marked as taken ("true")
        - if card is alreaddy grabbed from list a new number is gererated until a free card is found.*/
        java.util.Random random = new java.util.Random();
        boolean[] cardTaken = new boolean[deck.length];

        ChanceCard[] cardList = new ChanceCard[deck.length];
        for(int i = 0; i < deck.length; i++){ //makes sure cardlist points to the cards and not the array of cards...
            cardList[i] = deck[i];
            cardTaken[i] = false;
        }

        int nummer = random.nextInt(deck.length);
        for(int i = 0; i < deck.length; i++){
            while(cardTaken[nummer] == true){
                nummer = random.nextInt(deck.length);
            }
            deck[i] = cardList[nummer];
            cardTaken[nummer] = true;
        }
    }

    public ChanceCard draw() {
        if (cardCount >= deck.length){ //resets count when all cards have been drawn
            cardCount = 0;
        }

        return deck[cardCount++];//returns drawn card and THEN adds 1 to cardCount
        //return deck[6];
    }


    
}