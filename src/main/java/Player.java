import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity{

    public Player(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
    }

    @Override
    ArrayList<Card> guess(Card roomCurrIn) {
        return null;
    }
}