import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealCardsTests {
    @Test
    public void testDealCards() {
        //Todo add factory pattern for making players and bots

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Bot> bots = new ArrayList<>();

        Player bob = new Player("Bob", new int[]{1, 10});
        Bot robo = new Bot("Robo-Bob", new int[]{10, 1}, new BotAllAvailableInfoDeductionStrategy());


        players.add(bob);
        bots.add(robo);

        ArrayList<ArrayList<Card>> solutionAndExtraCards = GameplayActions.dealCards(players, bots);

        ArrayList<Card> mysterySolution = solutionAndExtraCards.get(0);
        ArrayList<Card> poolCards = solutionAndExtraCards.get(1);
        ArrayList<Card> allCards = solutionAndExtraCards.get(2);

        // Test if players were dealt cards
        assertEquals(bob.getHand().size(), 11);
        assertEquals(robo.getHand().size(), 11);

        // Test if a solution is chosen
        assertEquals(mysterySolution.size(), 3);

        // Test if no cards are left after dealing
        assertEquals(poolCards.size(), 0);

        // Test that all cards array is generated
        assertEquals(allCards.size(), 25);
    }
}
