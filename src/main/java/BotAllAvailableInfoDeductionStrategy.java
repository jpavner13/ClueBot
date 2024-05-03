import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BotAllAvailableInfoDeductionStrategy implements IBotDeductionStrategy {


    // Called when a new card is deduced to see if any others can be deduced as well
    // This method assumes other players try to show the minimum amount of cards
    public void updateInsights(String playerName, Card deducedCard, Bot bot) {
        // Stored for recursive calls, I know two arrayLists is wack, but it's less complicated than hashing
        ArrayList<Card> otherNewlyDeducedCards = new ArrayList<>();
        ArrayList<String> otherNewlyDeducedCardsOwner = new ArrayList<>();

        for (Map.Entry<String, ArrayList<ArrayList<Card>>> guessSet : bot.playersPastReveals.entrySet()) {
            List<ArrayList<Card>> toRemove = new ArrayList<>();

            for (ArrayList<Card> pastReveal : guessSet.getValue()) {
                String cardOwner = guessSet.getKey();
                if (pastReveal.contains(deducedCard) && cardOwner.equals(playerName)) {
                    toRemove.add(pastReveal);
                }
                else if (pastReveal.contains(deducedCard)) {
                    pastReveal.remove(deducedCard);
                    if (pastReveal.size() <= 1) {
                        Card newlyDiscoveredCard = pastReveal.get(0);
                        if (!otherNewlyDeducedCards.contains(newlyDiscoveredCard)) {
                            bot.cardsDeducedNotSolution.add(newlyDiscoveredCard);
                            bot.otherPlayerHands.get(cardOwner).add(newlyDiscoveredCard);
                            otherNewlyDeducedCards.add(newlyDiscoveredCard);
                            otherNewlyDeducedCardsOwner.add(cardOwner);
                        }
                        toRemove.add(pastReveal);
                    }
                }
            }
            guessSet.getValue().removeAll(toRemove);
        }
        for (int i =  0 ; i < otherNewlyDeducedCards.size(); i++) {
            updateInsights(otherNewlyDeducedCardsOwner.get(i), otherNewlyDeducedCards.get(i), bot);
        }

    }

    public void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed, Bot bot) {

        ArrayList<Card> guessCards = new ArrayList<>();
        guessCards.add(roomGuessed);
        guessCards.add(weaponGuessed);
        guessCards.add(suspectGuessed);

        // Check if bot already knows revealing player has one of the guessed cards, skip if so
        if (!bot.otherPlayerHands.get(reveler.getPlayerName()).contains(roomGuessed) || !bot.otherPlayerHands.get(reveler.getPlayerName()).contains(weaponGuessed)
                || !bot.otherPlayerHands.get(reveler.getPlayerName()).contains(suspectGuessed)) {
            int numKnownCards = 0;
            for (Card card : guessCards) {
                if (bot.cardsDeducedNotSolution.contains(card)) {
                    numKnownCards++;
                }
            }
            guessCards.removeAll(bot.cardsDeducedNotSolution);
            // Check if bot knows what card was shown, skip if all cards already known
            if (numKnownCards <= 1) { // Card shown unknown
                bot.playersPastReveals.get(reveler.getPlayerName()).add(guessCards);
            }
            else if (numKnownCards == 2) { // Card shown deduced
                Card newlyDeducedCard = guessCards.get(0);
                bot.cardsDeducedNotSolution.add(newlyDeducedCard);
                bot.otherPlayerHands.get(reveler.getPlayerName()).add(newlyDeducedCard);
                updateInsights(reveler.getPlayerName(), newlyDeducedCard, bot);
            }

        }

        System.out.println("I, " + bot.getPlayerName() + " just saw " + guesser.getPlayerName() + " get shown a card by " + reveler.getPlayerName() +
                " when the guess was: " + roomGuessed.getCardName() + ", " + weaponGuessed.getCardName() + ", " + suspectGuessed.getCardName() + ".");
    }

    public void getShownCard(Card shownCard, Entity revealer, Bot bot) {
        bot.otherPlayerHands.get(revealer.getPlayerName()).add(shownCard);
        bot.cardsDeducedNotSolution.add(shownCard);
        updateInsights(revealer.getPlayerName(), shownCard, bot);
        // V This can be replaced by an observer pattern later
        System.out.println("I, " + bot.getPlayerName() + " was just shown " + shownCard.getCardName() + " by " + revealer.getPlayerName());
    }
}
