import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;

public class Bot extends Entity {
    // TODO We will likely have to refactor this code so it is an interface as to support different types of bots

    HashMap<String, ArrayList<Card>> otherPlayerHands;
    ArrayList<Card> allPossibleCards;

    public Bot(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
        otherPlayerHands = new HashMap<>();
        allPossibleCards = new ArrayList<>();
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
