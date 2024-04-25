import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayWindowPage implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    GameBoard gameBoard = new GameBoard();

    DisplayWindowPage(){
        // Set the layout of the panel to GridLayout with 24 rows and 29 columns
        panel.setLayout(new GridLayout(29, 24));

        // Calculate the preferred size for each cell
        int cellWidth = 5; // Adjust as needed
        int cellHeight = 5; // Adjust as needed

        // Add JLabels to represent elements of your 24x29 array
        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 24; j++) {
                String labelText = gameBoard.getTileName(i, j);

                JLabel label = new JLabel(labelText);
                label.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border to labels
                label.setPreferredSize(new Dimension(cellWidth, cellHeight)); // Set preferred size
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
                        label.setBackground(Color.WHITE); // Orange-Red
                        break;
                    case "Green":
                        label.setBackground(Color.GREEN); // Crimson
                        break;
                    case "Peacock":
                        label.setBackground(new Color(50, 100, 160));
                        break;
                    case "Plum":
                        label.setBackground(new Color(100, 50, 180));
                        break;
                    default:
                        // Check if the label text contains "Door"
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

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBounds(50, 100, 700, 500);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
