import java.util.ArrayList;

public class Bot {
    private final String botName;
    private int[] boardPosition;
    private final ArrayList<Card> cardsInHand;

    public Bot (String name, int[] startingBoardPosition){
        this.botName = name;
        this.boardPosition = startingBoardPosition;
        cardsInHand = new ArrayList<Card>();
    }

    public String getPlayerName(){
        return this.botName;
    }

    public int[] getBoardPosition(){
        return this.boardPosition;
    }

    public void setBoardPosition(int[] newBoardPosition){
        this.boardPosition = newBoardPosition;
    }

    public void addCardToHand(Card newCard){
        this.cardsInHand.add(newCard);
    }

    public ArrayList<Card> getHand(){
        return this.cardsInHand;
    }
}
