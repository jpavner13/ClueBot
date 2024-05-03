public interface IBotDeductionStrategy {
    public void getShownCard(Card shownCard, Entity revealer, Bot bot);
    public void watchCardReveal(Entity guesser, Entity reveler, Card roomGuessed, Card weaponGuessed, Card suspectGuessed, Bot bot);
}
