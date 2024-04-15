import java.util.ArrayList;
import java.util.List;

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

    public List<int[]> getAdjacentMoves()
    {
        int[] currPosition = this.getBoardPosition();

        GameBoard board = GameBoard.getInstance();
        String[][] boardArray = board.getBoard();

        int boardWidth = boardArray[0].length;
        int boardHeight = boardArray.length;

        // At most there can be 4 possible moves.
        List<int[]> possibleMoves = new ArrayList<>();

        // Move left / west.
        if ( (currPosition[0] > 0) && (boardArray[currPosition[0] - 1][currPosition[1]].equals("Empty")) )
        {
            int[] move = {currPosition[0] - 1, currPosition[1]};
            possibleMoves.add(move);
        }

        // Move up / north.
        if ( (currPosition[1] > 0) && (boardArray[currPosition[0]][currPosition[1] - 1].equals("Empty")) )
        {
            int[] move = {currPosition[0], currPosition[1] - 1};
            possibleMoves.add(move);
        }

        // Move right / east.
        if ( (currPosition[0] < boardWidth - 1) && (boardArray[currPosition[0] + 1][currPosition[1]].equals("Empty")) )
        {
            int[] move = {currPosition[0] + 1, currPosition[1]};
            possibleMoves.add(move);
        }

        // Move down / south.
        if ( (currPosition[1] < boardHeight - 1) && (boardArray[currPosition[0]][currPosition[1] + 1].equals("Empty")) )
        {
            int[] move = {currPosition[0], currPosition[1] + 1};
            possibleMoves.add(move);
        }

        return possibleMoves;
    }
}
