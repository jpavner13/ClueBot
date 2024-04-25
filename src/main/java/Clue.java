import java.util.ArrayList;

public class Clue {
    //This class is only called to initialize the game and run its "play" method, which will call relevant
    //methods from other classes

    private ArrayList<Player> players;
    private ArrayList<Bot> bots;
    private ArrayList<Card> mysterySolution;
    private ArrayList<Card> poolCards;
    private ArrayList<Card> allCards;

    public Clue(ArrayList<Player> players, ArrayList<Bot> bots) {
        this.players = players;
        this.bots = bots;
        ArrayList<ArrayList<Card>> solutionAndExtraCards = GameplayActions.dealCards(players, bots);

        mysterySolution = solutionAndExtraCards.get(0);
        poolCards = solutionAndExtraCards.get(1);
        allCards = solutionAndExtraCards.get(2);

        //Make all players aware of the existence of other players and the deck being used
        GameplayActions.seeOtherPlayersAndPossibleCards(players, bots, allCards);
    }

    public void playGame() {
        // Main gameplay loop
    }

}
