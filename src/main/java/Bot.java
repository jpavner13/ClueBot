import java.util.ArrayList;

public class Bot extends Entity {

    public Bot(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
    }

    @Override
    ArrayList<Card> guess(Card roomCurrIn) {
        return null;
    }
}
