import java.util.ArrayList;
import java.util.Random;

public class Player{
    private final String playerName;
    private int[] boardPosition;

    private ArrayList<Weapons> weaponsInHand;
    private ArrayList<Players> playersInHand;
    private ArrayList<Rooms> roomsInHand;

    public Adventurer (String name, int[] startingBoardPosition){
        this.playerName = name;
        this.boardPosition = startingBoardPosition;
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

    }
}