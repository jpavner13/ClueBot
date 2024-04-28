import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BotShowCardTests {
    // Bot will show the only card in guess also in hand
    @Test
    public void testBotShowCardOneOption(){
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

        HashMap<String, ArrayList<Card>> cardsShownToMustard = new HashMap<>();
        cardsShownToMustard.put("Mustard", new ArrayList<Card>());
        cardsShownToMustard.get("Mustard").add(sortedDeck.get(0).get(0));

        Card botChosenCardToShow = testBot.showCard(testPlayer, sortedDeck.get(2).get(0), sortedDeck.get(1).get(1), sortedDeck.get(0).get(1));

        System.out.println(botChosenCardToShow.getCardName());

        assertEquals(botChosenCardToShow.getCardName(), "Kitchen");
    }

    // Bot will show the card it has already shown to the player
    @Test
    public void testBotShowCardAlreadyShown(){
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

        HashMap<String, ArrayList<Card>> cardsShownToMustard = new HashMap<>();
        cardsShownToMustard.put("Mustard", new ArrayList<Card>());
        cardsShownToMustard.get("Mustard").add(sortedDeck.get(0).get(0));

        testBot.cardsShownToOtherPlayers = cardsShownToMustard;

        Card botChosenCardToShow = testBot.showCard(testPlayer, sortedDeck.get(2).get(0), sortedDeck.get(1).get(0), sortedDeck.get(0).get(0));

        System.out.println(botChosenCardToShow.getCardName());

        assertEquals(botChosenCardToShow.getCardName(), "Scarlet");
    }

    // Bot will show the card it has shown to the most opponents
    @Test
    public void testBotShowToMostPeople(){
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

        Player testPlayer1 = new Player("Mustard", new int[] {0,0});

        HashMap<String, ArrayList<Card>> cardsShownToOthers = new HashMap<>();
        cardsShownToOthers.put("Mustard", new ArrayList<Card>());
        cardsShownToOthers.put("Plum", new ArrayList<Card>());
        cardsShownToOthers.put("White", new ArrayList<Card>());

        cardsShownToOthers.get("Plum").add(sortedDeck.get(0).get(0));
        cardsShownToOthers.get("White").add(sortedDeck.get(0).get(0));

        testBot.cardsShownToOtherPlayers = cardsShownToOthers;

        Card botChosenCardToShow = testBot.showCard(testPlayer1, sortedDeck.get(2).get(0), sortedDeck.get(1).get(0), sortedDeck.get(0).get(0));

        System.out.println(botChosenCardToShow.getCardName());

        assertEquals(botChosenCardToShow.getCardName(), "Scarlet");
    }
}
