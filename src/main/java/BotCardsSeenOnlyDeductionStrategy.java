public class BotCardsSeenOnlyDeductionStrategy implements IBotDeductionStrategy {
    public void getShownCard(Card shownCard, Entity revealer, Bot bot) {
        bot.otherPlayerHands.get(revealer.getPlayerName()).add(shownCard);
        bot.cardsDeducedNotSolution.add(shownCard);
        // V This can be replaced by an observer pattern later
        System.out.println("I, " + bot.getPlayerName() + " was just shown " + shownCard.getCardName() + " by " + revealer.getPlayerName());
    }

    public void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed, Bot bot)
    {
        //
    }
}
