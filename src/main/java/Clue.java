import java.util.ArrayList;

public class Clue {
    //This class is only called to initialize the game and run its "play" method, which will call relevant
    //methods from other classes

    private ArrayList<Player> players;
    private ArrayList<Bot> bots;
    private ArrayList<Card> mysterySolution;
    private ArrayList<Card> poolCards;

    public Clue(ArrayList<Player> players, ArrayList<Bot> bots) {
        this.players = players;
        this.bots = bots;
    }

    public void playGame() {

        // This method also deals cards to individual players
        ArrayList<ArrayList<Card>> solutionAndExtraCards = GameplayActions.dealCards(players, bots);

        mysterySolution = solutionAndExtraCards.get(0);
        poolCards = solutionAndExtraCards.get(1);
    }

}
