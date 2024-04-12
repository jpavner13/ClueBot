import java.util.ArrayList;
import java.util.Random;

public class Player{
    private final String playerName;
    private int[] boardPosition;
    private final ArrayList<Card> cardsInHand;

    public Player (String name, int[] startingBoardPosition){
        this.playerName = name;
        this.boardPosition = startingBoardPosition;
        cardsInHand = new ArrayList<Card>();
    }

    public String getPlayerName(){
        return this.playerName;
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