import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ClueBotTests extends javax.swing.JFrame{
    @Test
    public void testClueBot() throws InterruptedException, MalformedURLException, PropertyVetoException {
        ArrayList<Bot> bots = new ArrayList<Bot>();

        String botName = "Mustard";
        int[] startingPos = {28, 7};
        Bot bot = new Bot(botName, startingPos, new BotAllAvailableInfoDeductionStrategy());

        bots.add(bot);

        ArrayList<Player> players = getPlayers();

        ArrayList<ArrayList<Card>> solutionAndExtraCards = GameplayActions.dealCards(players, bots);

        ArrayList<Card> mysterySolution = solutionAndExtraCards.get(0);
        ArrayList<Card> poolCards = solutionAndExtraCards.get(1);
        ArrayList<Card> allCards = solutionAndExtraCards.get(2);

        //Find the "pool" card
        int indexOfPoolCard = 0;
        for (int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).getCardName().equals("Pool")) {
                indexOfPoolCard = i;
            }
        }
        Card poolCard = allCards.get(indexOfPoolCard);

        bot.storePoolCard(poolCard);

        DisplayWindowPage display = new DisplayWindowPage(bot, allCards, players);

        GameplayActions.seeOtherPlayersAndPossibleCards(players, bots, allCards);

        for(Card card : bot.getHand()){
            display.addCardToHand(card.getCardName());
        }

        while (display.frame.isVisible()) {
            Thread.sleep(100); // Sleep for a short interval to avoid CPU usage
        }
    }

    private static ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        int[] peacockPos = {0,6};
        Player peacock = new Player("Peacock", peacockPos);
        int[] plumPos = {0,20};
        Player plum = new Player("Plum", plumPos);
        int[] greenPos = {9,0};
        Player green = new Player("Green", greenPos);
        int[] whitePos = {19,0};
        Player white = new Player("White", whitePos);
        int[] scarletPos = {28,18};
        Player scarlet = new Player("Scarlet", scarletPos);
        players.add(peacock);
        players.add(plum);
        players.add(green);
        players.add(white);
        players.add(scarlet);
        return players;
    }

    // Bot should go to the pool and make a final guess
    @Test
    public void testClueBotSolutionKnown() throws InterruptedException, MalformedURLException, PropertyVetoException {
        ArrayList<Bot> bots = new ArrayList<Bot>();

        String botName = "Mustard";
        int[] startingPos = {28, 7};
        Bot bot = new Bot(botName, startingPos, new BotAllAvailableInfoDeductionStrategy());

        bots.add(bot);

        ArrayList<Player> players = getPlayers();

        ArrayList<ArrayList<Card>> solutionAndExtraCards = GameplayActions.dealCards(players, bots);

        ArrayList<Card> mysterySolution = solutionAndExtraCards.get(0);
        ArrayList<Card> poolCards = solutionAndExtraCards.get(1);
        ArrayList<Card> allCards = solutionAndExtraCards.get(2);

        ArrayList<Card> cardsNotInSolution = new ArrayList<>(allCards);
        cardsNotInSolution.removeAll(mysterySolution);

        // Ensure bot knows the solution
        for (Card card : cardsNotInSolution) {

            if (!bot.cardsDeducedNotSolution.contains(card)){
                bot.cardsDeducedNotSolution.add(card);
            }
        }

        //Find the "pool" card
        int indexOfPoolCard = 0;
        for (int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).getCardName().equals("Pool")) {
                indexOfPoolCard = i;
            }
        }
        Card poolCard = allCards.get(indexOfPoolCard);

        bot.storePoolCard(poolCard);

        DisplayWindowPage display = new DisplayWindowPage(bot, allCards, players);

        GameplayActions.seeOtherPlayersAndPossibleCards(players, bots, allCards);

        for(Card card : bot.getHand()){
            display.addCardToHand(card.getCardName());
        }

        while (display.frame.isVisible()) {
            Thread.sleep(100); // Sleep for a short interval to avoid CPU usage
        }
    }
}
