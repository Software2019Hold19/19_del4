package ChanceDeck;

import GUI.GUIController;
import GameBoard.GameBoard;
import GameBoard.OwnableField;
import Main.Player;
import Main.Translator;

/**
 * TaxChanceCard
 */
public class TaxChanceCard extends ChanceCard{

    private int houseTax;
    private int hotelTax;

    public TaxChanceCard(String descriptionString, int priceForHouse, int priceForHotel) {
        super(descriptionString);
        this.houseTax = priceForHouse;
        this.hotelTax = priceForHotel;
    }

    @Override
    public boolean drawn(Player player, GameBoard board ) {

        int houseCnt = getHousesAndHotels(player, board)[0];
        int hotelCnt = getHousesAndHotels(player, board)[1];
        int taxToPay = (houseCnt * this.houseTax) + (hotelCnt * this.hotelTax);
        player.addBal(-taxToPay);
                
        return false;
    }

    private int[] getHousesAndHotels(Player player, GameBoard board) {
        int result[] = {0, 0};

        for (OwnableField field : board.getOwnableBoard()) {
            if (field.getOwner().equals(player.getName())) {
                if (field.getHouseLevel() < 5) {
                    result[0] += field.getHouseLevel();
                }
                else if (field.getHouseLevel() == 5) {
                    result[1]++;
                }
            }
        }
        return result;
    }
    

    
}