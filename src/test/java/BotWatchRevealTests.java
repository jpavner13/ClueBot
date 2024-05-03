import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotWatchRevealTests {
    // Bot will make a card deduction based on who showed a card to whom and the guess
    @Test
    public void testBotWatchRevealNewDeduction(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0}, new BotAllAvailableInfoDeductionStrategy());

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        testBot.allPossibleCards = combinedDeck;

        Player testPlayer1 = new Player("Mustard", new int[] {0,0});
        Player testPlayer2 = new Player("Plum", new int[] {0,0});

        HashMap<String, ArrayList<Card>> otherPlayerHands = new HashMap<>();
        ArrayList<Card> cardsDeducedNotSolution = new ArrayList<>();
        HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals = new HashMap<>();

        cardsDeducedNotSolution.add(sortedDeck.get(0).get(0));
        cardsDeducedNotSolution.add(sortedDeck.get(1).get(0));
        cardsDeducedNotSolution.add(sortedDeck.get(2).get(0));

        otherPlayerHands.put("Mustard", new ArrayList<>());
        otherPlayerHands.put("Plum", new ArrayList<>());

        playersPastReveals.put("Plum", new ArrayList<ArrayList<Card>>());

        Card green = sortedDeck.get(0).get(1);
        Card patio = sortedDeck.get(2).get(1);

        playersPastReveals.get("Plum").add(new ArrayList<Card>());
        playersPastReveals.get("Plum").get(0).add(green);
        playersPastReveals.get("Plum").get(0).add(patio);

        System.out.println(playersPastReveals.get("Plum").get(0).get(0).getCardName());
        System.out.println(playersPastReveals.get("Plum").get(0).get(1).getCardName());

        testBot.otherPlayerHands = otherPlayerHands;
        testBot.cardsDeducedNotSolution = cardsDeducedNotSolution;
        testBot.playersPastReveals = playersPastReveals;

        testBot.watchCardReveal(testPlayer2, testPlayer1, sortedDeck.get(2).get(0), sortedDeck.get(1).get(0), green);

        // Make sure bot knows Green is no longer a possible solution
        assertTrue(testBot.cardsDeducedNotSolution.contains(green));
        // Make sure bot knows Patio is no longer a possible solution
        assertTrue(testBot.otherPlayerHands.get("Mustard").contains(green));
        // Make sure bot knows Patio is in player "Plum" hand
    }

    // Bot will store the guess info for later use
    @Test
    public void testBotWatchRevealNoNewDeduction(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0}, new BotAllAvailableInfoDeductionStrategy());

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        testBot.allPossibleCards = combinedDeck;

        Player testPlayer1 = new Player("Mustard", new int[] {0,0});
        Player testPlayer2 = new Player("Plum", new int[] {0,0});

        HashMap<String, ArrayList<Card>> otherPlayerHands = new HashMap<>();
        ArrayList<Card> cardsDeducedNotSolution = new ArrayList<>();
        HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals = new HashMap<>();

        cardsDeducedNotSolution.add(sortedDeck.get(0).get(0));
        cardsDeducedNotSolution.add(sortedDeck.get(1).get(0));
        cardsDeducedNotSolution.add(sortedDeck.get(2).get(0));

        otherPlayerHands.put("Mustard", new ArrayList<>());
        otherPlayerHands.put("Plum", new ArrayList<>());

        playersPastReveals.put("Plum", new ArrayList<ArrayList<Card>>());
        playersPastReveals.put("Mustard", new ArrayList<ArrayList<Card>>());

        Card green = sortedDeck.get(0).get(1);
        Card patio = sortedDeck.get(2).get(1);

        testBot.otherPlayerHands = otherPlayerHands;
        testBot.cardsDeducedNotSolution = cardsDeducedNotSolution;
        testBot.playersPastReveals = playersPastReveals;

        testBot.watchCardReveal(testPlayer2, testPlayer1, patio, sortedDeck.get(1).get(0), green);

        // Make sure bot did not make any new deductions
        assertEquals(testBot.cardsDeducedNotSolution.size(), 3);
        // Make sure bot stored the guess for later use
        assertEquals(testBot.playersPastReveals.get("Mustard").size(), 1);
    }
}
