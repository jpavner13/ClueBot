import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.TextUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayWindowPage {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    String cardListText = ("<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Suspects:</b></u><br>" +
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

    JEditorPane cardList = new JEditorPane("text/html", cardListText);

    String cardsInHandString = "<html><div style='text-align: center; line-height: 2;'><font size='5'><br><u><b>Cards In Hand:</b></u><br>";
    JEditorPane cardsInHand = new JEditorPane("text/html", cardsInHandString);
    GameBoard gameBoard = new GameBoard();

    DisplayWindowPage(){
        frame.setLayout(null);
        panel.setLayout(new GridLayout(29, 24));

        int cellWidth = 5;
        int cellHeight = 5;

        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 24; j++) {
                String labelText = gameBoard.getTileName(i, j);

                JLabel label = new JLabel(labelText);
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


                panel.add(label);
            }
        }

        panel.setBounds(300, 10, 1200, 800);

        cardList.setEditable(false);
        cardList.setFocusable(false);
        cardList.setOpaque(true);
        cardList.setBounds(60, 60, 180, 700);

        cardsInHand.setEditable(false);
        cardsInHand.setFocusable(false);
        cardsInHand.setOpaque(true);
        cardsInHand.setBounds(60, 820, 1600, 200);

        frame.add(cardList);
        frame.add(cardsInHand);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
