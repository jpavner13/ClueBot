import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class BotGuessTests {

    // Bot will guess cards not in hand
    @Test
    public void testBotGuess(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0});

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        ArrayList<Card> deducedCardsNotSolution = new ArrayList<>();
        deducedCardsNotSolution.add(sortedDeck.get(0).get(0));
        deducedCardsNotSolution.add(sortedDeck.get(1).get(0));
        deducedCardsNotSolution.add(sortedDeck.get(2).get(0));

        testBot.cardsDeducedNotSolution = deducedCardsNotSolution;
        testBot.allPossibleCards = combinedDeck;

        ArrayList<Card> botGuess = testBot.guess(sortedDeck.get(2).get(1));

        for (Card card : botGuess) {
            System.out.println(card.getCardName() + " ");
        }

        assertNotNull(botGuess);

    }

    // Bot will be forced to guess solution as that is its only option
    @Test
    public void testBotGuessWhenNoOptionForUsingHandInGuess(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0});

        // Bot has no hand in this scenario

        // Add all cards but one option to deduced cards
        ArrayList<Card> deducedCardsNotSolution = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < sortedDeck.get(i).size() - 1; j ++) {
                deducedCardsNotSolution.add(sortedDeck.get(i).get(j));
            }
        }

        testBot.cardsDeducedNotSolution = deducedCardsNotSolution;
        testBot.allPossibleCards = combinedDeck;

        ArrayList<Card> botGuess = testBot.guess(sortedDeck.get(2).get(9));

        for (Card card : botGuess) {
            System.out.println(card.getCardName() + " ");
        }

        assertEquals(botGuess.get(0).getCardName(), "Axe");
        assertEquals(botGuess.get(1).getCardName(), "White");
        assertEquals(botGuess.get(2).getCardName(), "Observatory");

    }

    // Bot should choose to guess only cards in its hand
    @Test
    public void testBotGuessWhenShouldUseCardInHandInGuess(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0});

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        // Add all cards but one option to deduced cards
        ArrayList<Card> deducedCardsNotSolution = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < sortedDeck.get(i).size() - 1; j ++) {
                deducedCardsNotSolution.add(sortedDeck.get(i).get(j));
            }
        }

        testBot.cardsDeducedNotSolution = deducedCardsNotSolution;
        testBot.allPossibleCards = combinedDeck;

        ArrayList<Card> botGuess = testBot.guess(sortedDeck.get(2).get(0));

        for (Card card : botGuess) {
            System.out.println(card.getCardName() + " ");
        }

        assertEquals(botGuess.get(0).getCardName(), "Rope");
        assertEquals(botGuess.get(1).getCardName(), "Scarlet");
        assertEquals(botGuess.get(2).getCardName(), "Kitchen");

    }
}
