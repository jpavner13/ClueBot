import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotGetTargetRoomsTests {

    // Bot will return only rooms that are solution candidates
    @Test
    public void testGetTargetRooms(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0}, new BotAllAvailableInfoDeductionStrategy());

        // Add all cards but one option to deduced cards
        ArrayList<Card> deducedCardsNotSolution = new ArrayList<>();

        for (int j = 0; j < sortedDeck.get(2).size() - 3; j ++) {
            deducedCardsNotSolution.add(sortedDeck.get(2).get(j));
        }

        testBot.cardsDeducedNotSolution = deducedCardsNotSolution;
        testBot.allPossibleCards = combinedDeck;

        ArrayList<Card> candidateRooms = testBot.getTargetRooms();

        for (Card card : candidateRooms) {
            System.out.println(card.getCardName() + " ");
        }

        assertEquals(candidateRooms.get(0).getCardName(), "Guest House");
        assertEquals(candidateRooms.get(1).getCardName(), "Hall");
        assertEquals(candidateRooms.get(2).getCardName(), "Observatory");

    }

}
