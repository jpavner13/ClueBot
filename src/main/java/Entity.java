import java.util.ArrayList;

public abstract class Entity {
    private final String entityName;
    private int[] boardPosition;
    private final ArrayList<Card> cardsInHand;

    public Entity (String name, int[] startingBoardPosition){
        this.entityName = name;
        this.boardPosition = startingBoardPosition;
        cardsInHand = new ArrayList<Card>();
    }

    public String getPlayerName(){
        return this.entityName;
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

    abstract ArrayList<Card> guess(Card roomCurrIn);
}
