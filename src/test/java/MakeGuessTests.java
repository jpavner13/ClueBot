import io.cucumber.java.bs.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakeGuessTests {
    @Test
    public void testDealCards() {
        //Todo add factory pattern for making players and bots

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Bot> bots = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>();

        Player bob = new Player("Scarlet", new int[]{1, 10});
        Bot robo = new Bot("Mustard", new int[]{10, 1});
        Player jim = new Player("Peacock", new int[]{15, 15});

        players.add(bob);
        players.add(jim);
        bots.add(robo);

        Card theater = new Card("Theater", CardTypes.RoomCard);
        Card rope = new Card("Rope", CardTypes.WeaponCard);
        Card scarlet = new Card("Scarlet", CardTypes.SuspectCard);
        Card mustard = new Card("Mustard", CardTypes.SuspectCard);
        Card pistol = new Card("Pistol", CardTypes.WeaponCard);

        allCards.add(rope);
        allCards.add(scarlet);

        bob.addCardToHand(theater);
        bob.addCardToHand(rope);
        bob.addCardToHand(scarlet);

        jim.addCardToHand(mustard);
        robo.addCardToHand(pistol);

        GameplayActions.makeGuess(theater, jim, players, bots, allCards);
    }
}
