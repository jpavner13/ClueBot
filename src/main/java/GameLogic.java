import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameLogic {
    public int[] findClosestDoor(ArrayList<Card> rooms, GameBoard gameBoard, int currRow, int currCol){
        int[] closestDoor = null;
        int closestDistance = Integer.MAX_VALUE;
        for(Card room : rooms) {
            for (int i = 0; i < 29; i++) {
                for (int j = 0; j < 24; j++) {
                    if (Objects.equals(gameBoard.getTileName(i, j), (room.getCardName() + " Door"))){
                        int distance = Math.abs(currCol - j) + Math.abs(currRow - i);
                        if(distance < closestDistance){
                            closestDoor = new int[]{i, j};
                            closestDistance = distance;
                        }
                    }
                }
            }
        }
        return closestDoor;
    }

    public boolean isPlayer(String playerName) {
        return(Objects.equals(playerName, "Mustard") || Objects.equals(playerName, "Scarlet") || Objects.equals(playerName, "White") || Objects.equals(playerName, "Green") || Objects.equals(playerName, "Peacock") || Objects.equals(playerName, "Plum"));
    }

    public boolean isRoomDoor(String roomName) {
        return(Objects.equals(roomName, "Spa Door") || Objects.equals(roomName, "Theater Door") || Objects.equals(roomName, "Living Room Door") || Objects.equals(roomName, "Observatory Door") || Objects.equals(roomName, "Patio Door") || Objects.equals(roomName, "Pool Door") || Objects.equals(roomName, "Hall Door") || Objects.equals(roomName, "Kitchen Door") || Objects.equals(roomName, "Dining Room Door") || Objects.equals(roomName, "Guest House Door"));
    }

    public int[] MoveLogic(GameBoard gameBoard, int currRow, int currCol, Color[][] origionalColor, Bot bot, JPanel panel){
        Random random = new Random();

        int roll1 = random.nextInt(6) + 1;
        int roll2 = random.nextInt(6) + 1;
        int totalRoll = roll1 + roll2;

        int oldCurrRow = currRow;
        int oldCurrCol = currCol;

        // CALL function to get the best target door
        ArrayList<Card> roomCandidates = bot.getTargetRooms();
        int[] closestDoor = findClosestDoor(roomCandidates, gameBoard, currRow, currCol);

        bot.setMovementTarget(closestDoor[0], closestDoor[1]);
        bot.executeOptimalMovements(totalRoll);

        int[] newPos = bot.getBoardPosition();

        currRow = newPos[0];
        currCol = newPos[1];

        Component[] components = panel.getComponents();
        JButton desiredButton = (JButton) components[currRow * 24 + currCol];
        JButton oldButton = (JButton) components[oldCurrRow * 24 + oldCurrCol];

        oldButton.setText(gameBoard.getTileName(oldCurrRow, oldCurrCol));
        oldButton.setBackground(origionalColor[oldCurrRow][oldCurrCol]);

        desiredButton.setText(bot.getPlayerName());
        desiredButton.setBackground(new Color(222, 222, 11));

        return new int[]{totalRoll, oldCurrCol, oldCurrRow, currRow, currCol};
    }

    public String[] concatenateArrays(String[]... arrays) {
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
}
