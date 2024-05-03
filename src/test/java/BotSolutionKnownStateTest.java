import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotSolutionKnownStateTest {

    // Bot knows solution in set-up, so should move towards the pool
    @Test
    public void testGetTargetRooms() {
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[]{0, 0}, new BotAllAvailableInfoDeductionStrategy());

        ArrayList<Card> deducedCardsNotSolution = new ArrayList<>();

        // Add all cards but one option of each type to deduced cards
        for (int i = 0; i < sortedDeck.size(); i++) {
            for (int j = 0; j < sortedDeck.get(i).size() - 1; j++) {
                deducedCardsNotSolution.add(sortedDeck.get(i).get(j));
            }
        }

        //Remove "Pool" from valid solutions
        int indexOfPoolCard = 0;
        for (int i = 0; i < combinedDeck.size(); i++) {
            if (combinedDeck.get(i).getCardName().equals("Pool")) {
                indexOfPoolCard = i;
            }
        }
        Card poolCard = combinedDeck.get(indexOfPoolCard);

        testBot.storePoolCard(poolCard);
        testBot.cardsDeducedNotSolution = deducedCardsNotSolution;
        testBot.allPossibleCards = combinedDeck;

        ArrayList<Card> candidateRooms = testBot.getTargetRooms();

        for (Card card : candidateRooms) {
            System.out.println(card.getCardName() + " ");
        }

        assertEquals(candidateRooms.get(0).getCardName(), "Pool");

    }
}
