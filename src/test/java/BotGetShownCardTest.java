import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotGetShownCardTest {
    // Bot will now know shown card is not solution, but gain no further insights based on past guesses
    @Test
    public void testBotGetShownNoOtherDeductions(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0});

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        testBot.allPossibleCards = combinedDeck;

        Player testPlayer = new Player("Mustard", new int[] {0,0});

        HashMap<String, ArrayList<Card>> otherPlayerHands = new HashMap<>();
        ArrayList<Card> cardsDeducedNotSolution = new ArrayList<>();
        HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals = new HashMap<>();

        otherPlayerHands.put("Mustard", new ArrayList<>());

        testBot.otherPlayerHands = otherPlayerHands;
        testBot.cardsDeducedNotSolution = cardsDeducedNotSolution;
        testBot.playersPastReveals = playersPastReveals;

        testBot.getShownCard(sortedDeck.get(0).get(1), testPlayer);

        // Make sure bot knows Green is no longer a possible solution
       assertEquals(testBot.cardsDeducedNotSolution.get(0).getCardName(), "Green");
       // Make sure bot knows Green is in player "Scarlet" hand
        assertEquals(testBot.otherPlayerHands.get("Mustard").get(0).getCardName(), "Green");
    }

    // Bot will now know shown card is not solution, and deduce another card based on past guesses
    @Test
    public void testBotGetShownWithNewDeduction(){
        ArrayList<ArrayList<Card>> sortedDeck = GameplayActions.createDeck();

        ArrayList<Card> combinedDeck = new ArrayList<>();
        combinedDeck.addAll(sortedDeck.get(0));
        combinedDeck.addAll(sortedDeck.get(1));
        combinedDeck.addAll(sortedDeck.get(2));

        Bot testBot = new Bot("Scarlet", new int[] {0,0});

        testBot.addCardToHand(sortedDeck.get(0).get(0));
        testBot.addCardToHand(sortedDeck.get(1).get(0));
        testBot.addCardToHand(sortedDeck.get(2).get(0));

        testBot.allPossibleCards = combinedDeck;

        Player testPlayer = new Player("Mustard", new int[] {0,0});

        HashMap<String, ArrayList<Card>> otherPlayerHands = new HashMap<>();
        ArrayList<Card> cardsDeducedNotSolution = new ArrayList<>();
        HashMap<String, ArrayList<ArrayList<Card>>> playersPastReveals = new HashMap<>();

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

        testBot.getShownCard(green, testPlayer);

        // Make sure bot knows Green is no longer a possible solution
        assertTrue(testBot.cardsDeducedNotSolution.contains(green));
        // Make sure bot knows Patio is no longer a possible solution
        assertTrue(testBot.cardsDeducedNotSolution.contains(patio));
        // Make sure bot knows Green is in player "Scarlet" hand
        assertTrue(testBot.otherPlayerHands.get("Mustard").contains(green));
        // Make sure bot knows Patio is in player "Plum" hand
        assertTrue(testBot.otherPlayerHands.get("Plum").contains(patio));
    }

}
