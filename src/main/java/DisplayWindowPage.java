import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class DisplayWindowPage {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    String playTurnButtonText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><u><b>Play Turn:</b></u><br>");
    JButton playTurnButton = new JButton();
    //String[] suspects = {"Miss Scarlett", "Colonel Mustard", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Professor Plum"};
    String[] suspects = {"Scarlet", "Mustard", "White", "Green", "Peacock", "Plum"};
    //String[] suspectsAndNull = {"Miss Scarlett", "Colonel Mustard", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Professor Plum", "No One"};
    String[] suspectsAndNull = {"Scarlet", "Mustard", "White", "Green", "Peacock", "Plum", "No One"};
    String[] weapons = {"Rope", "Candlestick", "Knife", "Pistol", "Bat", "Dumbbell", "Trophy", "Poison", "Axe"};
    String[] rooms = {"Kitchen", "Patio", "Spa", "Dining Room", "Pool", "Theatre", "Living Room", "Guest House", "Hall", "Observatory"};
    JComboBox whoGuessed = new JComboBox(suspects);
    JComboBox suspectList = new JComboBox(suspects);
    JComboBox weaponsList = new JComboBox(weapons);
    JComboBox roomsList = new JComboBox(rooms);
    JComboBox whoShowed = new JComboBox(suspectsAndNull);
    String logGuessButtonText = ("<html><div style='text-align: center;'><font size='5'><u><b>Log Guess:</b></u><br>");
    JButton logGuessButton = new JButton();
    static String cardListText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Suspects:</b></u><br>" +
            "Miss Scarlett<br>" +
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

    static JEditorPane cardList = new JEditorPane("text/html", cardListText);
    String cardsInHandString = "<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Cards In Hand:</b></u><br>";
    JEditorPane cardsInHand = new JEditorPane("text/html", cardsInHandString);
    String logGuessLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Log a Player Guess:</b></u><br>");
    JLabel logGuessLabel = new JLabel();
    String guesserLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Guesser:</b><br>");
    JLabel guesserLabel = new JLabel();
    String whoLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Who:</b><br>");
    JLabel whoLabel = new JLabel();
    String whatLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>What:</b><br>");
    JLabel whatLabel = new JLabel();
    String whereLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Where:</b><br>");
    JLabel whereLabel = new JLabel();
    String showerLabelText = ("<html><div style='text-align: center; line-height: 2;'><font size='3'><b>Who Showed a Card?:</b><br>");
    JLabel showerLabel = new JLabel();
    JLabel background = new JLabel();
    GameBoard gameBoard = new GameBoard();
    boolean isMovingPlayer = false;
    JButton lastSelectedCell = null;
    int lastSelectedCellRow, lastSelectedCellCol;
    Color[][] origionalColor = new Color[29][24];
    String movingPlayerName = null;
    Bot bot;
    int currRow, currCol;
    JButton botButton = null;


    DisplayWindowPage(Bot newBot, ArrayList<Card> allCards, ArrayList<Player> allPlayers) throws MalformedURLException, PropertyVetoException {
        bot = newBot;

        frame.setLayout(null);
        panel.setLayout(new GridLayout(29, 24));
        playTurnButton.setLayout(null);

        playTurnButton.setText(playTurnButtonText);
        logGuessButton.setText(logGuessButtonText);
        logGuessLabel.setText(logGuessLabelText);
        guesserLabel.setText(guesserLabelText);
        whoLabel.setText(whoLabelText);
        whatLabel.setText(whatLabelText);
        whereLabel.setText(whereLabelText);
        showerLabel.setText(showerLabelText);

        ImageIcon icon = createImageIcon("background.jpeg", "Background Image");
        background.setIcon(icon);

        int cellWidth = 5;
        int cellHeight = 5;

        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 24; j++) {
                String labelText = gameBoard.getTileName(i, j);

                JButton label = new JButton(labelText);
                int finalRow = i;
                int finalCol = j;
                label.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedCell = (JButton) e.getSource();
                        String playerName = clickedCell.getText();

                        if (!isMovingPlayer) {
                            if(isPlayer(playerName)) {
                                isMovingPlayer = true;
                                lastSelectedCell = clickedCell;
                                movingPlayerName = playerName;

                                lastSelectedCellRow = finalRow;
                                lastSelectedCellCol = finalCol;
                            }
                        } else {
                            clickedCell.setText(movingPlayerName);
                            clickedCell.setBackground(lastSelectedCell.getBackground());

                            botButton = clickedCell;

                            if(Objects.equals(movingPlayerName, bot.getPlayerName())){
                                currCol = finalCol;
                                currRow = finalRow;
                                int[] newPosition = {finalRow, finalCol};
                                bot.setBoardPosition(newPosition);
                            }

                            lastSelectedCell.setText(gameBoard.getTileName(lastSelectedCellRow, lastSelectedCellCol));
                            lastSelectedCell.setBackground(origionalColor[lastSelectedCellRow][lastSelectedCellCol]);

                            isMovingPlayer = false;
                            lastSelectedCell = null;
                            movingPlayerName = null;
                        }
                    }
                });

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setPreferredSize(new Dimension(cellWidth, cellHeight));
                label.setOpaque(true);

                switch(labelText){
                    case "Empty":
                        label.setBackground(Color.DARK_GRAY);
                        break;
                    case "Clue":
                        label.setBackground(Color.RED);
                        break;
                    case "Pool":
                        label.setBackground(Color.CYAN);
                        break;
                    case "Spa":
                        label.setBackground(Color.ORANGE);
                        break;
                    case "Living Room":
                        label.setBackground(Color.YELLOW);
                        break;
                    case "Observatory":
                        label.setBackground(new Color(128, 0, 128));
                        break;
                    case "Hall":
                        label.setBackground(new Color(255, 105, 180));
                        break;
                    case "Patio":
                        label.setBackground(new Color(192, 192, 192));
                        break;
                    case "Dining Room":
                        label.setBackground(new Color(0, 0, 255));
                        break;
                    case "Kitchen":
                        label.setBackground(new Color(128, 128, 0));
                        break;
                    case "Guest House":
                        label.setBackground(new Color(255, 215, 0));
                        break;
                    case "Theater":
                        label.setBackground(new Color(0, 255, 255));
                        break;
                    case "Mustard":
                        label.setBackground(new Color(222, 222, 11));
                        break;
                    case "Scarlet":
                        label.setBackground(new Color(220, 20, 60));
                        break;
                    case "White":
                        label.setBackground(Color.WHITE);
                        break;
                    case "Green":
                        label.setBackground(Color.GREEN);
                        break;
                    case "Peacock":
                        label.setBackground(new Color(50, 100, 160));
                        break;
                    case "Plum":
                        label.setBackground(new Color(100, 50, 180));
                        break;
                    default:
                        if (labelText.contains("Door")) {
                            label.setBackground(new Color(139, 69, 19)); // Brown color
                        } else {
                            label.setBackground(Color.WHITE); // Default color
                        }
                        break;
                }

                if(labelText.equals(bot.getPlayerName())){
                    currRow = finalRow;
                    currCol = finalCol;
                    botButton = label;
                }

                origionalColor[i][j] = label.getBackground();

                panel.add(label);
            }
        }

        panel.setBounds(250, 10, 1200, 800);
        panel.setFocusable(true);

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

                int roll1 = 6;
                int roll2 = 6;
                int totalRoll = roll1 + roll2;

                JButton oldButton = botButton;
                if(oldButton != null) {
                    oldButton.setText(gameBoard.getTileName(currRow, currCol));
                    oldButton.setBackground(origionalColor[currRow][currCol]);
                }

                System.out.print("Moving from " + currRow + ", " + currCol + " to ");

                bot.setMovementTarget(8, 22);
                bot.executeOptimalMovements(totalRoll);

                int[] newPos = bot.getBoardPosition();

                currRow = newPos[1];
                currCol = newPos[0];

                Component[] components = panel.getComponents();
                JButton desiredButton = (JButton) components[currRow * 24 + currCol];

                desiredButton.setText(bot.getPlayerName());
                desiredButton.setBackground(new Color(222, 222, 11));

                botButton = desiredButton;

                System.out.println(currRow + ", " + currCol);

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
                if(isRoomDoor(tileName)) {
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

                String cardShownLabelText = ("<html><style>h1{text-align:center;}</style><div text-align:center;><font size='3'><u><b>Card Shown:</b></u><br>");
                JLabel cardShownLabel = new JLabel(cardShownLabelText);
                cardShownLabel.setBounds(50, 200, 200, 30);

                String[] noCardShown = {"No Card Shown"};
                String[] allCardsList = concatenateArrays(suspects, weapons, rooms, noCardShown);
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
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        guessFrame.setVisible(false);

                        String cardSeen = (String) cardShown.getSelectedItem();
                        Card seenCard = null;
                        for(Card card : allCards){
                            if (Objects.equals(card.getCardName(), cardSeen)){
                                seenCard = card;
                                break;
                            }
                        }
                        String playerWhoShowedCard = (String) playerWhoShowed.getSelectedItem();
                        Player playerShowing = null;
                        for(Player player : allPlayers){
                            if (Objects.equals(player.getPlayerName(), playerWhoShowedCard)){
                                playerShowing = player;
                            }
                        }

                        // CALL Update Knowledge function here
                        assert playerShowing != null;
                        bot.getShownCard(seenCard, playerShowing);

                        eliminateCard(cardSeen);
                        cardList.updateUI();
                    }
                });

                guessFrame.add(closeButton);
                guessFrame.add(guessLabel);
                guessFrame.add(cardShownLabel);
                guessFrame.add(cardShown);
                guessFrame.add(playerShowedLabel);
                guessFrame.add(playerWhoShowed);
                guessFrame.add(guessArea);

                frame.add(guessFrame);
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
                bot.watchCardReveal(whoGuessedCard, whoShowedCard, roomCard, weaponsCard, suspectCard);
            }
        });

        background.setOpaque(true);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                background.setBounds(0, -1000, frame.getWidth(), frame.getHeight() + 2000);
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

    private boolean isPlayer(String playerName) {
        return(Objects.equals(playerName, "Mustard") || Objects.equals(playerName, "Scarlet") || Objects.equals(playerName, "White") || Objects.equals(playerName, "Green") || Objects.equals(playerName, "Peacock") || Objects.equals(playerName, "Plum"));
    }

    private boolean isRoomDoor(String roomName) {
        return(Objects.equals(roomName, "Spa Door") || Objects.equals(roomName, "Theater Door") || Objects.equals(roomName, "Living Room Door") || Objects.equals(roomName, "Observatory Door") || Objects.equals(roomName, "Patio Door") || Objects.equals(roomName, "Pool Door") || Objects.equals(roomName, "Hall Door") || Objects.equals(roomName, "Kitchen Door") || Objects.equals(roomName, "Dining Room Door") || Objects.equals(roomName, "Guest House Door"));
    }

    public void addCardToHand(String newCard){
        cardsInHand.setText(cardsInHandString + newCard + "<br>");
        cardsInHandString = cardsInHandString + newCard + "<br>";
        eliminateCard(newCard);
    }

    protected static ImageIcon createImageIcon(String path, String description) {
        URL imgURL = DisplayWindowPage.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(new ImageIcon(imgURL, description).getImage().getScaledInstance(1750, 1100,  Image.SCALE_SMOOTH));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static String[] concatenateArrays(String[]... arrays) {
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }

        String[] result = new String[totalLength];
        int currentIndex = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, result, currentIndex, array.length);
            currentIndex += array.length;
        }
        return result;
    }

    public static void eliminateCard(String cardName){
        cardListText = cardListText.replace(cardName, "<font color='red'><s>" + cardName + "</s></font>");
        cardList.setText(cardListText);
    }
}
