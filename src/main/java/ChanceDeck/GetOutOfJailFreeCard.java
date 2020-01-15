package ChanceDeck;

import GameBoard.GameBoard;
import Main.Player;

public class GetOutOfJailFreeCard extends ChanceCard {

    public GetOutOfJailFreeCard(String descriptionString) {
        super(descriptionString);
    }

    @Override
    public boolean drawn(Player player, GameBoard board){
        player.setJailCard(true);
        return false;
    }
}
