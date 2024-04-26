import java.util.*;

public class Bot extends Entity {
    HashMap<String, ArrayList<Card>> otherPlayerHands;
    ArrayList<Card> allPossibleCards;
    // While the following arrayList can be derived from other cached data, this reduces internal list searching
    ArrayList<Card> cardsDeducedNotSolution;
    HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals;
    HashMap<String, ArrayList<Card>> cardsShownToOtherPlayers;

    public Bot(String name, int[] startingBoardPosition) {
        super(name, startingBoardPosition);
        otherPlayerHands = new HashMap<>();
        allPossibleCards = new ArrayList<>();
        cardsDeducedNotSolution = new ArrayList<>();
        playersPastReveals = new HashMap<>();
        cardsShownToOtherPlayers = new HashMap<>();
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

    @Override
    ArrayList<Card> guess(Card roomCurrIn, ArrayList<Card> allCards) {
        return null;
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

    @Override
    void getShownCard(Card shownCard, Entity reveler) {
        otherPlayerHands.get(reveler.getPlayerName()).add(shownCard);
        cardsDeducedNotSolution.add(shownCard);
        updateInsights(reveler.getPlayerName(), shownCard);
        // V This can be replaced by an observer pattern later
        System.out.println("I, " + getPlayerName() + " was just shown " + shownCard.getCardName() + " by " + reveler.getPlayerName());
    }

    @Override
    void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed) {

        ArrayList<Card> guessCards = new ArrayList<>();
        guessCards.add(roomGuessed);
        guessCards.add(weaponGuessed);
        guessCards.add(suspectGuessed);

        // Check if bot already knows revealing player has one of the guessed cards, skip if so
        if (!otherPlayerHands.get(reveler.getPlayerName()).contains(roomGuessed) || !otherPlayerHands.get(reveler.getPlayerName()).contains(weaponGuessed)
                || !otherPlayerHands.get(reveler.getPlayerName()).contains(suspectGuessed)) {
            int numKnownCards = 0;
            for (Card card : guessCards) {
                if (cardsDeducedNotSolution.contains(card)) {
                    numKnownCards++;
                }
            }
            guessCards.removeAll(cardsDeducedNotSolution);
            // Check if bot knows what card was shown, skip if all cards already known
            if (numKnownCards <= 1) { // Card shown unknown
                playersPastReveals.get(reveler.getPlayerName()).add(guessCards);
            }
            else if (numKnownCards == 2) { // Card shown deduced
                Card newlyDeducedCard = guessCards.get(0);
                cardsDeducedNotSolution.add(newlyDeducedCard);
                otherPlayerHands.get(reveler.getPlayerName()).add(newlyDeducedCard);
                updateInsights(reveler.getPlayerName(), newlyDeducedCard);
            }

        }

        System.out.println("I, " + getPlayerName() + " just saw " + guesser.getPlayerName() + " get shown a card by " + reveler.getPlayerName() +
                " when the guess was: " + roomGuessed.getCardName() + ", " + weaponGuessed.getCardName() + ", " + suspectGuessed.getCardName() + ".");
    }

    // Called when a new card is deduced to see if any others can be deduced as well
    // This method assumes other players try to show the minimum amount of cards
    private void updateInsights(String playerName, Card newlyDeducedCard) {
        // Stored for recursive calls, I know two arrayLists is wack, but it's less complicated than hashing
        ArrayList<Card> otherNewlyDeducedCards = new ArrayList<>();
        ArrayList<String> otherNewlyDeducedCardsOwner = new ArrayList<>();

        for (Map.Entry<String, ArrayList<ArrayList<Card>>> guessSet : playersPastReveals.entrySet()) {
            List<ArrayList<Card>> toRemove = new ArrayList<>();

            for (ArrayList<Card> pastReveal : guessSet.getValue()) {
                String cardOwner = guessSet.getKey();
                if (pastReveal.contains(newlyDeducedCard) && cardOwner.equals(playerName)) {
                    toRemove.add(pastReveal);
                }
                else if (pastReveal.contains(newlyDeducedCard)) {
                    pastReveal.remove(newlyDeducedCard);
                    if (pastReveal.size() <= 1) {
                        Card newlyDiscoveredCard = pastReveal.get(0);
                        if (!otherNewlyDeducedCards.contains(newlyDiscoveredCard)) {
                            cardsDeducedNotSolution.add(newlyDeducedCard);
                            otherPlayerHands.get(cardOwner).add(newlyDeducedCard);
                            otherNewlyDeducedCards.add(newlyDeducedCard);
                            otherNewlyDeducedCardsOwner.add(cardOwner);
                        }
                        toRemove.add(pastReveal);
                    }
                }
            }
            guessSet.getValue().removeAll(toRemove);
        }
        for (int i =  0 ; i < otherNewlyDeducedCards.size(); i++) {
            updateInsights(otherNewlyDeducedCardsOwner.get(i), otherNewlyDeducedCards.get(i));
        }

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
