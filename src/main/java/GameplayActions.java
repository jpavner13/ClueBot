import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameplayActions {
    // This class will be used to store static methods that change the game-state. Decouples player and bot implementations.

        protected static ArrayList<ArrayList<Card>> dealCards(ArrayList<Player> players, ArrayList<Bot> bots) {

                ArrayList<ArrayList<Card>> deck = createDeck();
                ArrayList<Card> allCards = new ArrayList<>();
                ArrayList<Card> solution = new ArrayList<>();
                int totalPlayers = players.size() + bots.size();

                Random random = new Random();

                for (ArrayList<Card> cardType : deck) {
                        allCards.addAll(cardType);
                        int index = random.nextInt(cardType.size());
                        solution.add(cardType.remove(index));
                }

                ArrayList<Card> shuffledDeck = new ArrayList<>();

                for (ArrayList<Card> cardType : deck) {
                    shuffledDeck.addAll(cardType);
                }

                Collections.shuffle(shuffledDeck);
                int cardsPerPlayer = Math.floorDiv(shuffledDeck.size(), totalPlayers);

                for (Player player : players) {
                        for (int i = 0; i < cardsPerPlayer; i++) {
                                player.addCardToHand(shuffledDeck.remove(0));
                        }
                }
                for (Bot bot : bots) {
                        for (int i = 0; i < cardsPerPlayer; i++) {
                                bot.addCardToHand(shuffledDeck.remove(0));
                        }
                }

                ArrayList<ArrayList<Card>> solutionAndExtraCards = new ArrayList<>();
                solutionAndExtraCards.add(solution);
                solutionAndExtraCards.add(shuffledDeck);
                solutionAndExtraCards.add(allCards);

                return solutionAndExtraCards;
        }

        // This instantiation is necessary to have cards as objects with attributes rather having a slow dedicated "getCardType" method
        public static ArrayList<ArrayList<Card>> createDeck() {

                ArrayList<Card> suspects = new ArrayList<>();
                ArrayList<Card> weapons = new ArrayList<>();
                ArrayList<Card> rooms = new ArrayList<>();

                String[] suspectNames = {"Scarlet", "Green", "Mustard", "Plum", "Peacock", "White"};
                String[] weaponNames = {"Rope", "Candlestick", "Knife", "Pistol", "Bat", "Dumbbell", "Trophy", "Poison", "Axe"};
                String[] roomNames = {"Kitchen", "Patio", "Spa", "Dining Room", "Pool", "Theatre", "Living Room", "Guest House", "Hall", "Observatory"};

                for (String name : suspectNames) {
                        suspects.add(new Card(name, CardTypes.SuspectCard));
                }
                for (String name : weaponNames) {
                        weapons.add(new Card(name, CardTypes.WeaponCard));
                }
                for (String name : roomNames) {
                        rooms.add(new Card(name, CardTypes.RoomCard));
                }

                ArrayList<ArrayList<Card>> deck = new ArrayList<>();
                deck.add(suspects);
                deck.add(weapons);
                deck.add(rooms);

                return deck;
        }


        protected static void makeGuess(Card roomCurrIn, Entity guesser, ArrayList<Player> players, ArrayList<Bot> bots, ArrayList<Card> allCards) {

                ArrayList<Card> weaponAndSuspect = guesser.guess(roomCurrIn);
                Card weaponGuessed = weaponAndSuspect.get(0);
                Card suspectGuessed = weaponAndSuspect.get(1);

                ArrayList<Entity> otherPlayers = new ArrayList<>();
                otherPlayers.addAll(players);
                otherPlayers.addAll(bots);

                int guesserIndex = otherPlayers.indexOf(guesser);

                int i = guesserIndex + 1;
                while (i != guesserIndex) {
                        if (i >= otherPlayers.size()) {
                                i = 0;
                        }
                        else {
                                Entity checkedPlayer = otherPlayers.get(i);
                                ArrayList<Card> checkedPlayerHand = checkedPlayer.getHand();

                                if (checkedPlayerHand.contains(roomCurrIn) || checkedPlayerHand.contains(weaponGuessed)
                                        || checkedPlayerHand.contains(suspectGuessed)) {
                                        Card shownCard = checkedPlayer.showCard(guesser, roomCurrIn, weaponGuessed, suspectGuessed);
                                        guesser.getShownCard(shownCard, checkedPlayer);
                                        for (Entity uninvoledPlayer : otherPlayers) {
                                                if (uninvoledPlayer != checkedPlayer && uninvoledPlayer != guesser) {
                                                        uninvoledPlayer.watchCardReveal(guesser, checkedPlayer, roomCurrIn, weaponGuessed, suspectGuessed);
                                                }
                                        }
                                        break;
                                }
                                else {
                                        i++;
                                }
                        }
                }

                // Move guessed player to room where guess was made
                for (Entity player : otherPlayers) {
                        if (player.getPlayerName() == suspectGuessed.getCardName()) {
                                player.setBoardPosition(guesser.getBoardPosition());
                                break;
                        }
                }


        }

        public static void seeOtherPlayersAndPossibleCards(ArrayList<Player> players, ArrayList<Bot> bots, ArrayList<Card> allCards) {
                ArrayList<Entity> allPlayers = new ArrayList<>();
                allPlayers.addAll(players);
                allPlayers.addAll(bots);
                for (Entity player : allPlayers) {
                        player.seeOtherPlayers(allPlayers);
                        player.seeDeck(allCards);
                }
        }

}
