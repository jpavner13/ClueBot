import java.util.*;

public class Bot extends Entity {
    // TODO We will likely have to refactor this code so it is an interface as to support different types of bots

    HashMap<String, ArrayList<Card>> otherPlayerHands;
    ArrayList<Card> allPossibleCards;

    int targetXPosition, targetYPosition;

    public Bot(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
        otherPlayerHands = new HashMap<>();
        allPossibleCards = new ArrayList<>();
        targetXPosition = 0;
        targetYPosition = 0;
    }

    @Override
    ArrayList<Card> guess(Card roomCurrIn, ArrayList<Card> allCards) {
        return null;
    }

    //Todo this is a naive implementation. Change in later version.
    @Override
    Card showCard(Entity guesser, Card roomGuessed, Card weaponGuessed, Card suspectGuessed) {
        for (Card card : getHand()) {
            if (card == roomGuessed || card == weaponGuessed || card == suspectGuessed) {
                return card;
            }
        }
        //Failsafe, this line should never be reached
        return getHand().get(0);
    }

    //Todo this is a naive implementation. Change in later version.
    @Override
    void getShownCard(Card shownCard, Entity reveler) {
        otherPlayerHands.get(reveler.getPlayerName()).add(shownCard);
        // V This can be replaced by an observer pattern later
        System.out.println("I, " + getPlayerName() + " was just shown " + shownCard.getCardName() + " by " + reveler.getPlayerName());
    }

    //Todo this is a naive implementation. Change in later version.
    @Override
    void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed) {
        System.out.println("I, " + getPlayerName() + " just saw " + guesser.getPlayerName() + " get shown a card by " + reveler.getPlayerName() +
                " when the guess was: " + roomGuessed.getCardName() + ", " + weaponGuessed.getCardName() + ", " + suspectGuessed.getCardName() + ".");
    }

    protected void seeOtherPlayers(ArrayList<Entity> allPlayers) {
        for (Entity player : allPlayers) {
            if (!Objects.equals(player.getPlayerName(), this.getPlayerName())) {
                otherPlayerHands.put(player.getPlayerName(), new ArrayList<>());
            }
        }
    }

    protected void seeDeck(ArrayList<Card> allCards) {
        allPossibleCards = allCards;
    }

    // Returns the adjacent moves of any location x and y.
    private List<Position> getAdjacentMoves(int x, int y)
    {
        GameBoard board = GameBoard.getInstance();
        String[][] boardArray = board.getBoard();

        int boardWidth = boardArray[0].length;
        int boardHeight = boardArray.length;

        // At most there can be 4 possible moves.
        List<Position> possibleMoves = new ArrayList<>();

        // Move left / west.
        // Player can only go to a position if it is either a door or empty.
        if ( (x > 0) &&
                ((boardArray[y][x - 1].equals("Empty")) ||
                        boardArray[y][x - 1].contains("Door")) )
        {
            Position move = new Position(x - 1, y);
            possibleMoves.add(move);
        }

        // Move up / north.
        if ( (y > 0) &&
                (boardArray[y - 1][x].equals("Empty") ||
                        boardArray[y - 1][x].contains("Door")) )
        {
            Position move = new Position(x, y - 1);
            possibleMoves.add(move);
        }

        // Move right / east.
        if ( (x < boardWidth - 2) &&
                (boardArray[y][x + 1].equals("Empty") ||
                        boardArray[y][x + 1].contains("Door")) )
        {
            Position move = new Position(x + 1, y);
            possibleMoves.add(move);
        }

        // Move down / south.
        if ( (y < boardHeight - 2) &&
                (boardArray[y + 1][x].equals("Empty") ||
                        boardArray[y + 1][x].contains("Door")) )
        {
            Position move = new Position(x, y + 1);
            possibleMoves.add(move);
        }

        return possibleMoves;
    }

    public void executeOptimalMovements(int roll)
    {
        List<Position> moves = this.findOptimalMovement();

        for (int i = 0; i < roll; i++)
        {
            if (moves.isEmpty())
            {
                break;
            }

            Position currentMove = moves.remove(moves.size()-1);
            int[] positionToGoTo = {currentMove.x(), currentMove.y()};

            this.setBoardPosition(positionToGoTo);
        }

    }

    private boolean doesVisitedContainElement(List<Position[]> visited, Position element, int idx)
    {
        for (Position[] sequence : visited)
        {
            if ((sequence[idx].x() == element.x()) && (sequence[idx].y() == element.y()))
            {
                return true;
            }
        }
        return false;
    }

    private Position[] findParentElement(List<Position[]> visited, Position search)
    {
        for (Position[] sequence : visited)
        {
            if ((sequence[1].x() == search.x()) && (sequence[1].y() == search.y()))
            {
                return sequence;
            }
        }
        return null;
    }


    // Returns a queue of optimal movements toward the target as discovered by Breadth First Search.
    private List<Position> findOptimalMovement()
    {
        // Map from position to parent.
        List<Position[]> parentsAndPositions = new ArrayList<>();

        Queue<Position> frontier = new LinkedList<>();

        Position currentPosition = new Position(this.getBoardPosition()[0], this.getBoardPosition()[1]);

        frontier.add(currentPosition);

        while (!frontier.isEmpty())
        {
            Position consider = frontier.remove();

            // Have we already visited this node?
            if (this.doesVisitedContainElement(parentsAndPositions, consider, 0))
            {
                continue;
            }

            List<Position> movesHere = this.getAdjacentMoves(consider.x(), consider.y());

            for (Position move : movesHere)
            {
                Position[] moveSequence = {consider, move};
                parentsAndPositions.add(moveSequence);
                frontier.add(move);
            }

            if (consider.x() == this.getMovementTarget()[0] && consider.y() == this.getMovementTarget()[1])
            {
                break;
            }

        }

        // BFS done, find path now.
        List<Position> optimalMoves = new Stack<>();

        // If the target doesn't exist as a to-node in the parentsAndPositions array, return an empty stack, don't even bother looking.
        if (!this.doesVisitedContainElement(parentsAndPositions, new Position(this.getMovementTarget()[0], this.getMovementTarget()[1]), 1))
        {
            return optimalMoves;
        }

        Position pathPosition = new Position(parentsAndPositions.get(parentsAndPositions.size()-1)[0].x(), parentsAndPositions.get(parentsAndPositions.size()-1)[0].y());
        while (pathPosition != null && (pathPosition.x() != currentPosition.x()) || (pathPosition.y() != currentPosition.y()))
        {
            optimalMoves.add(pathPosition);
            Position[] visitedElement = this.findParentElement(parentsAndPositions, pathPosition);

            if (visitedElement == null)
            {
                // No such path exists.
                break;
            }

            pathPosition = visitedElement[0];
        }

        return optimalMoves;

    }

    public void setMovementTarget(int x, int y)
    {
        targetXPosition = x;
        targetYPosition = y;
    }

    public int[] getMovementTarget()
    {
        int[] target = {targetXPosition, targetYPosition};
        return target;
    }

    public boolean isAtTarget()
    {
        int[] currPosition = this.getBoardPosition();

        if ((currPosition[1] == targetYPosition) && (currPosition[0] == targetXPosition))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    
}
