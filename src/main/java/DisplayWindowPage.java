import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;

public class DisplayWindowPage {
    Bot bot;

    JFrame frame;
    JPanel panel;
    JButton playTurnButton, logGuessButton;
    static JButton lastSelectedCell, botButton = null;
    static JEditorPane cardList, cardsInHand;
    JLabel logGuessLabel, guesserLabel, whoLabel, whatLabel, whereLabel, showerLabel;
    static JLabel background;
    JComboBox whoGuessed, suspectList, weaponsList, roomsList, whoShowed;


    String[] suspects = {"Scarlet", "Mustard", "White", "Green", "Peacock", "Plum"};
    String[] suspectsAndNull = {"Scarlet", "Mustard", "White", "Green", "Peacock", "Plum", "No One"};
    String[] weapons = {"Rope", "Candlestick", "Knife", "Pistol", "Bat", "Dumbbell", "Trophy", "Poison", "Axe"};
    String[] rooms = {"Kitchen", "Patio", "Spa", "Dining Room", "Pool", "Theatre", "Living Room", "Guest House", "Hall", "Observatory"};
    static String cardListText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Suspects:</b></u><br>" +
            "Miss Scarlet<br>" +
            "Colonel Mustard<br>" +
            "Mrs. White<br>" +
            "Reverend Green<br>" +
            "Mrs. Peacock<br>" +
            "Professor Plum</font>" +
            "<br><br>" +
            "<font size='5'><u><b>Weapons:</b></u><br>" +
            "Rope<br>" +
            "Candlestick<br>" +
            "Knife<br>" +
            "Pistol<br>" +
            "Bat<br>" +
            "Dumbbell<br>" +
            "Trophy<br>" +
            "Poison<br>" +
            "Axe</font>" +
            "<br><br>" +
            "<font size='5'><u><b>Rooms:</b></u><br>" +
            "Kitchen<br>" +
            "Patio<br>" +
            "Spa<br>" +
            "Dining Room<br>" +
            "Pool<br>" +
            "Theatre<br>" +
            "Living Room<br>" +
            "Guest House<br>" +
            "Hall<br>" +
            "Observatory</font></div></html>");
    String cardsInHandString = "<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Cards In Hand:</b></u><br>";


    static Color[][] origionalColor;
    static boolean isMovingPlayer = false;
    static String movingPlayerName = null;
    static int currRow, currCol;
    static int lastSelectedCellRow, lastSelectedCellCol;



    static GameBoard gameBoard;
    GameLogic gameLogic = new GameLogic();
    DisplayElementFactory elementFactory = new DisplayElementFactory();


    DisplayWindowPage(Bot newBot, ArrayList<Card> allCards, ArrayList<Player> allPlayers) throws MalformedURLException, PropertyVetoException {
        bot = newBot;
        frame = new JFrame();
        playTurnButton = new JButton();
        whoGuessed = new JComboBox(suspects);
        suspectList = new JComboBox(suspects);
        weaponsList = new JComboBox(weapons);
        roomsList = new JComboBox(rooms);
        whoShowed = new JComboBox(suspectsAndNull);
        logGuessButton = new JButton();
        logGuessLabel = new JLabel();
        guesserLabel = new JLabel();
        whoLabel = new JLabel();
        whatLabel = new JLabel();
        whereLabel = new JLabel();
        showerLabel = new JLabel();
        background = new JLabel();
        gameBoard = new GameBoard();
        cardsInHand = new JEditorPane("text/html", cardsInHandString);
        cardList = new JEditorPane("text/html", cardListText);
        origionalColor = new Color[29][24];

        frame.setLayout(null);
        playTurnButton.setLayout(null);

        String playTurnButtonText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><u><b>Play Turn:</b></u><br>");
        String logGuessButtonText = ("<html><div style='text-align: center;'><font size='5'><u><b>Log Guess:</b></u><br>");
        String logGuessLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Log a Player Guess:</b></u><br>");
        String guesserLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Guesser:</b><br>");
        String whoLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Who:</b><br>");
        String whatLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>What:</b><br>");
        String whereLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Where:</b><br>");
        String showerLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Who Showed a Card?:</b><br>");

        playTurnButton.setText(playTurnButtonText);
        logGuessButton.setText(logGuessButtonText);
        logGuessLabel.setText(logGuessLabelText);
        guesserLabel.setText(guesserLabelText);
        whoLabel.setText(whoLabelText);
        whatLabel.setText(whatLabelText);
        whereLabel.setText(whereLabelText);
        showerLabel.setText(showerLabelText);

        elementFactory.addBackground(background, frame);
        panel = elementFactory.buildBoard(gameBoard, gameLogic, bot, origionalColor);

        cardList.setEditable(false);
        cardList.setFocusable(false);
        cardList.setOpaque(true);
        cardList.setBounds(60, 60, 180, 700);

        cardsInHand.setEditable(false);
        cardsInHand.setFocusable(false);
        cardsInHand.setOpaque(true);
        cardsInHand.setBounds(660, 820, 400, 200);

        playTurnButton.setOpaque(true);
        playTurnButton.setBackground(Color.GREEN);
        playTurnButton.setBounds(1470, 60, 200, 100);
        playTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame guessFrame = new JFrame();
                guessFrame.setLayout(null);
                guessFrame.setBounds(200, 200, 600, 400);
                guessFrame.setVisible(true);

                // CALL function to move here (use currRow and currCol as inputs and update them on output)
                int[] moveResults = gameLogic.MoveLogic(gameBoard, currRow, currCol, origionalColor, bot, panel);

                int totalRoll = moveResults[0];
                int oldCurrRow = moveResults[1];
                int oldCurrCol = moveResults[2];
                currRow = moveResults[3];
                currCol = moveResults[4];

                String guessLabelText = ("<html><style>h1{text-align:center;}</style><div text-align:center;><h1><u><b>Guess Made:</b></u></h1><br>");
                JLabel guessLabel = new JLabel();
                guessLabel.setText(guessLabelText);
                guessLabel.setBounds(220, 10, 200, 50);

                String suspectGuess = "Empty";
                String weaponGuess = "Empty";
                String roomGuess = "Empty";

                int row = 0;
                int col = 0;

                int numCells = panel.getComponentCount();
                for (int i = 0; i < numCells; i++) {
                    Component component = panel.getComponent(i);
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        int x = button.getX();
                        int y = button.getY();
                        JButton buttonTemp = (JButton) component;
                        if (Objects.equals(buttonTemp.getText(), bot.getPlayerName())) {
                            row = i / 24;
                            col = i % 24;
                            break;
                        }
                    }
                }

                String roomName = null;
                Card roomCard = null;
                String tileName = gameBoard.getTileName(row, col);
                if(gameLogic.isRoomDoor(tileName)) {
                    roomName = gameBoard.getTileName(row, col).replaceAll(" Door", "");
                    for(Card card : allCards){
                        if (Objects.equals(card.getCardName(), roomName)){
                            roomCard = card;
                            break;
                        }
                    }

                    // CALL function to make guess here
                    ArrayList<Card> guess = bot.guess(roomCard);
                    weaponGuess = guess.get(0).getCardName();
                    suspectGuess = guess.get(1).getCardName();
                    roomGuess = guess.get(2).getCardName();
                }

                String guessText = ("<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>"+ suspectGuess + "</b><br>" +
                        "<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>"+ weaponGuess + "</b><br>" +
                        "<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>"+ roomGuess + "</b><br>");

                if(Objects.equals(suspectGuess, "Empty") && (Objects.equals(weaponGuess, "Empty") && (Objects.equals(roomGuess, "Empty")))) {
                    guessText = ("<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>"+ "No Guess Made" + "</b><br>");
                }

                JEditorPane guessArea = new JEditorPane("text/html", guessText);
                guessArea.setBounds(200, 45, 200, 150);

                String rollText = ("<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>Total Roll:</b><br><br>" + totalRoll);
                JEditorPane totalRollArea = new JEditorPane("text/html", rollText);
                totalRollArea.setBounds(10, 45, 150, 120);
                totalRollArea.setFocusable(false);

                String moveText = ("<html><div style='text-align: center; line-height: 1;'><font size='5'><br><b>" + bot.getPlayerName() + " moved from (" + oldCurrRow + ", " + oldCurrCol + ") to (" + currRow + ", " + currCol + ")</b><br><br>");
                JEditorPane moveArea = new JEditorPane("text/html", moveText);
                moveArea.setBounds(440, 45, 150, 120);
                moveArea.setFocusable(false);

                String cardShownLabelText = ("<html><style>h1{text-align:center;}</style><div text-align:center;><font size='3'><u><b>Card Shown:</b></u><br>");
                JLabel cardShownLabel = new JLabel(cardShownLabelText);
                cardShownLabel.setBounds(50, 200, 200, 30);

                String[] noCardShown = {"No Card Shown"};
                String[] allCardsList = gameLogic.concatenateArrays(suspects, weapons, rooms, noCardShown);
                JComboBox cardShown = new JComboBox(allCardsList);
                cardShown.setBounds(50, 225, 200, 25);
                cardShown.setFocusable(true);

                String playerShowedLabelText = ("<html><style>h1{text-align:center;}</style><div text-align:center;><font size='3'><u><b>Player who Showed Card:</b></u><br>");
                JLabel playerShowedLabel = new JLabel(playerShowedLabelText);
                playerShowedLabel.setBounds(350, 200, 200, 30);

                JComboBox playerWhoShowed = new JComboBox(suspects);
                playerWhoShowed.setBounds(350, 225, 200, 25);
                playerWhoShowed.setFocusable(true);

                JButton closeButton = new JButton("Confirm");
                closeButton.setBounds(200, 260, 200, 100);
                closeButton.setOpaque(true);

                boolean isInRoom = gameLogic.isRoomDoor(tileName);

                if(!isInRoom){
                    playerShowedLabel.setVisible(false);
                    playerWhoShowed.setVisible(false);
                    playerWhoShowed.setFocusable(false);
                    cardShownLabel.setVisible(false);
                    cardShown.setVisible(false);
                    cardShown.setFocusable(false);
                }

                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        guessFrame.setVisible(false);

                        if(isInRoom) {
                            String cardSeen = (String) cardShown.getSelectedItem();
                            Card seenCard = null;
                            for (Card card : allCards) {
                                if (Objects.equals(card.getCardName(), cardSeen)) {
                                    seenCard = card;
                                    break;
                                }
                            }
                            String playerWhoShowedCard = (String) playerWhoShowed.getSelectedItem();
                            Player playerShowing = null;
                            for (Player player : allPlayers) {
                                if (Objects.equals(player.getPlayerName(), playerWhoShowedCard)) {
                                    playerShowing = player;
                                }
                            }

                            // CALL Update Knowledge function here
                            assert playerShowing != null;
                            bot.getShownCard(seenCard, playerShowing);

                            eliminateCardVisual(bot);
                            cardList.updateUI();
                        }
                    }
                });

                guessFrame.add(closeButton);
                guessFrame.add(guessLabel);
                guessFrame.add(totalRollArea);
                guessFrame.add(moveArea);
                guessFrame.add(cardShownLabel);
                guessFrame.add(cardShown);
                guessFrame.add(playerShowedLabel);
                guessFrame.add(playerWhoShowed);
                guessFrame.add(guessArea);
            }
        });

        logGuessLabel.setOpaque(true);
        logGuessLabel.setBounds(1470, 220, 200, 70);

        guesserLabel.setOpaque(true);
        guesserLabel.setBounds(1470, 270, 200, 70);

        whoGuessed.setOpaque(true);
        whoGuessed.setEditable(false);
        whoGuessed.setFocusable(true);
        whoGuessed.setBounds(1470, 320, 200, 25);

        whoLabel.setOpaque(true);
        whoLabel.setBounds(1470, 340, 200, 70);

        suspectList.setOpaque(true);
        suspectList.setEditable(false);
        suspectList.setFocusable(true);
        suspectList.setBounds(1470, 390, 200, 25);

        whatLabel.setOpaque(true);
        whatLabel.setBounds(1470, 410, 200, 70);

        weaponsList.setOpaque(true);
        weaponsList.setEditable(false);
        weaponsList.setFocusable(true);
        weaponsList.setBounds(1470, 460, 200, 25);

        whereLabel.setOpaque(true);
        whereLabel.setBounds(1470, 480, 200, 70);

        roomsList.setOpaque(true);
        roomsList.setEditable(false);
        roomsList.setFocusable(true);
        roomsList.setBounds(1470, 530, 200, 25);

        showerLabel.setOpaque(true);
        showerLabel.setBounds(1470, 550, 200, 70);

        whoShowed.setOpaque(true);
        whoShowed.setEditable(false);
        whoShowed.setFocusable(true);
        whoShowed.setBounds(1470, 600, 200, 25);

        logGuessButton.setOpaque(true);
        logGuessButton.setBounds(1470, 670, 200, 100);
        logGuessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String whoGuessedInfo = (String) whoGuessed.getSelectedItem();
                String suspectGuessed = (String) suspectList.getSelectedItem();
                String weaponGuessed = (String) weaponsList.getSelectedItem();
                String roomGuessed = (String) roomsList.getSelectedItem();
                String whoShowedInfo = (String) whoShowed.getSelectedItem();

                Player whoGuessedCard = null;
                Card suspectCard = null;
                Card weaponsCard = null;
                Card roomCard = null;
                Player whoShowedCard = null;

                for(Card card : allCards){
                    String currCardName = card.getCardName();
                    if (Objects.equals(currCardName, suspectGuessed)) suspectCard = card;
                    else if (Objects.equals(currCardName, weaponGuessed)) weaponsCard = card;
                    else if (Objects.equals(currCardName, roomGuessed)) roomCard = card;
                }

                for(Player player : allPlayers){
                    String currCardName = player.getPlayerName();
                    if(Objects.equals(currCardName, whoGuessedInfo)) whoGuessedCard = player;
                    if(Objects.equals(currCardName, whoShowedInfo)) whoShowedCard = player;
                }

                // CALL function to witness Guess
                assert whoShowedCard != null;
                bot.watchCardReveal(whoGuessedCard, whoShowedCard, roomCard, weaponsCard, suspectCard);

                eliminateCardVisual(bot);
            }
        });

        frame.add(cardList);
        frame.add(cardsInHand);
        frame.add(panel);
        frame.add(playTurnButton);
        frame.add(suspectList);
        frame.add(weaponsList);
        frame.add(roomsList);
        frame.add(logGuessButton);
        frame.add(logGuessLabel);
        frame.add(whoGuessed);
        frame.add(whoShowed);
        frame.add(guesserLabel);
        frame.add(whoLabel);
        frame.add(whatLabel);
        frame.add(whereLabel);
        frame.add(showerLabel);
        frame.add(background);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void addCardToHand(String newCard){
        cardsInHand.setText(cardsInHandString + newCard + "<br>");
        cardsInHandString = cardsInHandString + newCard + "<br>";
        eliminateCardVisual(bot);
    }

    static ArrayList<Card> alreadyEliminated = new ArrayList<>();
    public static void eliminateCardVisual(Bot bot) {
        ArrayList<Card> cardsKnown = bot.getCardsDeducedNotSolution();
        for (Card currCard : cardsKnown) {
            if (!(alreadyEliminated.contains(currCard))) {
                String cardName = currCard.getCardName();
                alreadyEliminated.add(currCard);

                String textToReplace = switch (cardName) {
                    case "Scarlet" -> "Miss Scarlet";
                    case "Mustard" -> "Colonel Mustard";
                    case "White" -> "Mrs. White";
                    case "Green" -> "Reverend Green";
                    case "Peacock" -> "Mrs. Peacock";
                    case "Plum" -> "Professor Plum";
                    default -> cardName;
                };

                cardListText = cardListText.replace(textToReplace, "<font color='red'><s>" + textToReplace + "</s></font>");
                cardList.setText(cardListText);
            }
        }
    }
}