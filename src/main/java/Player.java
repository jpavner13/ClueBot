import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{

    public Player(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
    }

    @Override
    ArrayList<Card> guess(Card roomCurrIn) {
        return null;
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
        // Player can only go to a position if it is either a door or empty.
        if ( (currPosition[1] > 0) &&
                ((boardArray[currPosition[0]][currPosition[1] - 1].equals("Empty")) ||
                        boardArray[currPosition[0]][currPosition[1] - 1].contains("Door")) )
        {
            int[] move = {currPosition[0], currPosition[1] - 1};
            possibleMoves.add(move);
        }

        // Move up / north.
        if ( (currPosition[0] > 0) &&
                (boardArray[currPosition[0] - 1][currPosition[1]].equals("Empty") ||
                        boardArray[currPosition[0] - 1][currPosition[1]].contains("Door")) )
        {
            int[] move = {currPosition[0] - 1, currPosition[1]};
            possibleMoves.add(move);
        }

        // Move right / east.
        if ( (currPosition[1] < boardWidth - 1) &&
                (boardArray[currPosition[0]][currPosition[1] + 1].equals("Empty") ||
                        boardArray[currPosition[0]][currPosition[1] + 1].contains("Door")) )
        {
            int[] move = {currPosition[0], currPosition[1] + 1};
            possibleMoves.add(move);
        }

        // Move down / south.
        if ( (currPosition[0] < boardHeight - 1) &&
                (boardArray[currPosition[0] + 1][currPosition[1]].equals("Empty") ||
                        boardArray[currPosition[0] + 1][currPosition[1]].contains("Door")) )
        {
            int[] move = {currPosition[0] + 1, currPosition[1]};
            possibleMoves.add(move);
        }

        return possibleMoves;
    }
}