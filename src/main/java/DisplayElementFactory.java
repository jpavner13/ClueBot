import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Objects;

public class DisplayElementFactory {
    public JPanel buildBoard(GameBoard gameBoard, GameLogic gameLogic, Bot bot, Color[][] origionalColor){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(29, 24));

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

                        if (!DisplayWindowPage.isMovingPlayer) {
                            if(gameLogic.isPlayer(playerName)) {
                                DisplayWindowPage.isMovingPlayer = true;
                                DisplayWindowPage.lastSelectedCell = clickedCell;
                                DisplayWindowPage.movingPlayerName = playerName;
                                DisplayWindowPage.lastSelectedCellRow = finalRow;
                                DisplayWindowPage.lastSelectedCellCol = finalCol;
                            }
                        } else {
                            clickedCell.setText(DisplayWindowPage.movingPlayerName);
                            clickedCell.setBackground(DisplayWindowPage.lastSelectedCell.getBackground());

                            DisplayWindowPage.botButton = clickedCell;

                            if(Objects.equals(DisplayWindowPage.movingPlayerName, bot.getPlayerName())){
                                DisplayWindowPage.currCol = finalCol;
                                DisplayWindowPage.currRow = finalRow;
                                int[] newPosition = {finalRow, finalCol};
                                bot.setBoardPosition(newPosition);
                            }

                            DisplayWindowPage.lastSelectedCell.setText(gameBoard.getTileName(DisplayWindowPage.lastSelectedCellRow, DisplayWindowPage.lastSelectedCellCol));
                            DisplayWindowPage.lastSelectedCell.setBackground(origionalColor[DisplayWindowPage.lastSelectedCellRow][DisplayWindowPage.lastSelectedCellCol]);

                            DisplayWindowPage.isMovingPlayer = false;
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
                    DisplayWindowPage.currRow = finalRow;
                    DisplayWindowPage.currCol = finalCol;
                    DisplayWindowPage.botButton = label;
                }

                origionalColor[i][j] = label.getBackground();

                panel.add(label);
                panel.setBounds(250, 10, 1200, 800);
                panel.setFocusable(true);
            }
        }

        return panel;
    }

    public void addBackground(JLabel background, JFrame frame) {
        URL imgURL = DisplayWindowPage.class.getResource("background.jpeg");
        if (imgURL != null) {
            background.setIcon(new ImageIcon(new ImageIcon(imgURL, "Background Image").getImage().getScaledInstance(1750, 1100,  Image.SCALE_SMOOTH)));
        } else {
            System.err.println("Couldn't find file: " + "background.jpeg");
        }
        background.setOpaque(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                background.setBounds(0, -1000, frame.getWidth(), frame.getHeight() + 2000);
            }
        });
    }
}
