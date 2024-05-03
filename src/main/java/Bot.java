import java.util.*;

public class Bot extends Entity {
    HashMap<String, ArrayList<Card>> otherPlayerHands;
    ArrayList<Card> allPossibleCards;
    // While the following arrayList can be derived from other cached data, this reduces internal list searching
    ArrayList<Card> cardsDeducedNotSolution;
    HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals;
    HashMap<String, ArrayList<Card>> cardsShownToOtherPlayers;
    int targetXPosition, targetYPosition;
    // Solution known state
    boolean solutionKnown;
    Card poolCard;

    IBotDeductionStrategy botDeductionStrategy;

    public Bot(String name, int[] startingBoardPosition, IBotDeductionStrategy deductionStrategy) {
        super(name, startingBoardPosition);
        otherPlayerHands = new HashMap<>();
        allPossibleCards = new ArrayList<>();
        cardsDeducedNotSolution = new ArrayList<>();
        playersPastReveals = new HashMap<>();
        cardsShownToOtherPlayers = new HashMap<>();
        targetXPosition = 0;
        targetYPosition = 0;
        botDeductionStrategy = deductionStrategy;
        solutionKnown = false;
    }

    public ArrayList<Card> getCardsDeducedNotSolution(){
        return cardsDeducedNotSolution;
    }

    protected void seeOtherPlayers(ArrayList<Entity> allPlayers) {
        for (Entity player : allPlayers) {
            if (!Objects.equals(player.getPlayerName(), this.getPlayerName())) {
                otherPlayerHands.put(player.getPlayerName(), new ArrayList<>());
                playersPastReveals.put(player.getPlayerName(), new ArrayList<>());
                cardsShownToOtherPlayers.put(player.getPlayerName(), new ArrayList<>());
            }
        }
    }

    protected void seeDeck(ArrayList<Card> allCards) {
        allPossibleCards = allCards;
    }

    @Override
    public void addCardToHand(Card newCard){

        super.addCardToHand(newCard);
        cardsDeducedNotSolution.add(newCard);

    }

    // Game theory blurb: It is better to guess a card from hand than the known solution card, as any other
    // player who knows the cards in your hand will get the solution automatically if you guess it and it
    // goes through. Additionally, if the bot keeps guessing the solution to a card type and other players
    // know most cards of that type, they will likely infer the solution as well. If turn order was tracked
    // It would technically be best to guess a card in the hand of the last player(s) in the turn order if
    // their hand(s) is already known, as getting shown that card is effectively the same as the guess going through
    // all players.

    // Returns weapon, suspect, room in that order
    @Override
    ArrayList<Card> guess(Card roomCurrIn) {

        // Remove cards known to be in players hands
        ArrayList<Card> candidateCards = new ArrayList<>(allPossibleCards);
        candidateCards.removeAll(cardsDeducedNotSolution);

        ArrayList<Card> shuffledHand = new ArrayList<>(getHand());

        // Prevent predictable guessing patterns so players can't infer bot deductions
        Collections.shuffle(candidateCards);
        Collections.shuffle(shuffledHand);

        Card weaponGuess = null;
        Card suspectGuess = null;
        int numWeaponsPossible = 0;
        int numSuspectsPossible = 0;
        for (Card card : candidateCards) {
            if (card.getTypeOfCard() == CardTypes.WeaponCard) {
                weaponGuess = card;
                numWeaponsPossible++;
            }
        }
        for (Card card : candidateCards) {
            if (card.getTypeOfCard() == CardTypes.SuspectCard) {
                suspectGuess = card;
                numSuspectsPossible++;
            }
        }
        if (numWeaponsPossible == 1) {
            for (Card card : shuffledHand) {
                if (card.getTypeOfCard() == CardTypes.WeaponCard) {
                    weaponGuess = card;
                }
            }
        }
        if (numSuspectsPossible == 1) {
            for (Card card : shuffledHand) {
                if (card.getTypeOfCard() == CardTypes.SuspectCard) {
                    suspectGuess = card;
                }
            }
        }

        ArrayList<Card> botsGuess = new ArrayList<>();
        botsGuess.add(weaponGuess);
        botsGuess.add(suspectGuess);
        botsGuess.add(roomCurrIn);

        return botsGuess;
    }

    // This method is only invoked when the bot has already been verified as having a valid card to show
    @Override
    Card showCard(Entity guesser, Card roomGuessed, Card weaponGuessed, Card suspectGuessed) {
        String guesserName = guesser.getPlayerName();

        ArrayList<Card> combinedGuess = new ArrayList<>();
        combinedGuess.add(roomGuessed);
        combinedGuess.add(weaponGuessed);
        combinedGuess.add(suspectGuessed);

        // Only consider cards in bot's hand
        combinedGuess.retainAll(getHand());

        // If only one card is owned, show it
        if (combinedGuess.size() == 1) {
            return combinedGuess.get(0);
        }
        // If bot has shown this player any card in the guess, show that card again
        for (Card card : combinedGuess) {
            if (cardsShownToOtherPlayers.get(guesserName).contains(card)) {
                return card;
            }
        }
        // If two or more cards in hand from guess, show card that has been shown to the most other players
        ArrayList<Integer> amountPlayersCardShownTo = new ArrayList<>();
        for (int i = 0; i < combinedGuess.size(); i++) {
            amountPlayersCardShownTo.add(0);
            for (Map.Entry<String, ArrayList<Card>> cardsShown : cardsShownToOtherPlayers.entrySet()) {
                for (Card card : cardsShown.getValue()) {
                    if (card.equals(combinedGuess.get(i))) {
                        amountPlayersCardShownTo.set(i, amountPlayersCardShownTo.get(i) + 1);
                    }
                }
            }
        }
        if (!combinedGuess.isEmpty()) {
            return combinedGuess.get(Collections.max(amountPlayersCardShownTo));
        }

        //Failsafe, this line should never be reached
        return getHand().get(0);
    }

    public void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed)
    {
        checkIfSolutionKnown();
        if (!solutionKnown) {
            botDeductionStrategy.watchCardReveal(guesser, reveler, roomGuessed, weaponGuessed, suspectGuessed, this);
        }
    }

    void getShownCard(Card shownCard, Entity revealer) {
        botDeductionStrategy.getShownCard(shownCard, revealer, this);
    }

    private void checkIfSolutionKnown() {
        ArrayList<Card> candidateCards = new ArrayList<>(allPossibleCards);
        candidateCards.removeAll(cardsDeducedNotSolution);
        if (candidateCards.size() <= 3) {
            solutionKnown = true;
        }
    }

    // Returns rooms still needed to deduce, or room in hand if solved, or solution if no room card in hand.
    protected ArrayList<Card> getTargetRooms() {
        ArrayList<Card> targetRooms = new ArrayList<>();

        checkIfSolutionKnown();

        if (solutionKnown) {
            targetRooms.add(poolCard);
            return targetRooms;
        }

        // Remove cards known to be in players hands
        ArrayList<Card> candidateCards = new ArrayList<>(allPossibleCards);
        candidateCards.removeAll(cardsDeducedNotSolution);

        int numRoomsPossible = 0;

        for (Card card : candidateCards) {
            if (card.getTypeOfCard() == CardTypes.RoomCard) {
                targetRooms.add(card);
                numRoomsPossible++;
            }
        }
        if (numRoomsPossible == 1) {
            ArrayList<Card> roomCardsInHand = new ArrayList<>();
            for (Card card : getHand()) {
                if (card.getTypeOfCard() == CardTypes.RoomCard) {
                    roomCardsInHand.add(card);
                }
            }
            if (!roomCardsInHand.isEmpty()) {
                targetRooms.clear();
                targetRooms.addAll(roomCardsInHand);
            }
        }

        return targetRooms;
    }

    void storePoolCard(Card poolCardToSet){
        poolCard = poolCardToSet;
    }

    public boolean isSolutionKnown(){
        return solutionKnown;
    }

    // Returns the adjacent moves of any location x and y.
    private List<Position> getAdjacentMoves(int y, int x)
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
            Position move = new Position(y, x - 1);
            possibleMoves.add(move);
        }

        // Move up / north.
        if ( (y > 0) &&
                (boardArray[y - 1][x].equals("Empty") ||
                        boardArray[y - 1][x].contains("Door")) )
        {
            Position move = new Position(y - 1, x);
            possibleMoves.add(move);
        }

        // Move right / east.
        if ( (x < boardWidth - 1) &&
                (boardArray[y][x + 1].equals("Empty") ||
                        boardArray[y][x + 1].contains("Door")) )
        {
            Position move = new Position(y, x + 1);
            possibleMoves.add(move);
        }

        // Move down / south.
        if ( (y < boardHeight - 1) &&
                (boardArray[y + 1][x].equals("Empty") ||
                        boardArray[y + 1][x].contains("Door")) )
        {
            Position move = new Position(y + 1, x);
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

            Position currentMove = moves.remove(moves.size() - 1);
            int[] positionToGoTo = {currentMove.y(), currentMove.x()};

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

            List<Position> movesHere = this.getAdjacentMoves(consider.y(), consider.x());

            for (Position move : movesHere)
            {
                Position[] moveSequence = {consider, move};
                parentsAndPositions.add(moveSequence);
                frontier.add(move);
            }

            if (consider.x() == this.getMovementTarget()[1] && consider.y() == this.getMovementTarget()[0])
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

        Position[] lastPosition = parentsAndPositions.get(parentsAndPositions.size()-1);
        Position pathPosition = new Position(lastPosition[0].y(), lastPosition[0].x());

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

    public void setMovementTarget(int y, int x)
    {
        targetXPosition = x;
        targetYPosition = y;
    }

    public int[] getMovementTarget()
    {
        int[] target = {targetYPosition, targetXPosition};
        return target;
    }

    public boolean isAtTarget()
    {
        int[] currPosition = this.getBoardPosition();

        if ((currPosition[0] == targetYPosition) && (currPosition[1] == targetXPosition))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    
}

