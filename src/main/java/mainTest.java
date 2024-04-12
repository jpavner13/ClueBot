import java.util.HashMap;
import java.util.Map;

public class mainTest {
    public static void main(String[] args) {
        String[][] board = {
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa", "Peacock", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Plum", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater", "Clue", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater Door", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Clue", "Theater", "Theater", "Theater", "Theater", "Theater", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Spa Door", "Empty", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater", "Empty", "Living Room Door", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Empty", "Empty", "Theater", "Theater", "Theater", "Theater", "Theater", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Clue", "Observatory", "Observatory"},
                {"Spa", "Spa", "Spa", "Spa", "Spa", "Empty", "Empty", "Empty", "Theater", "Theater", "Theater Door", "Theater", "Theater", "Empty", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Living Room", "Empty", "Empty", "Observatory", "Observatory"},
                {"Empty", "Empty", "Empty", "Clue", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Living Room", "Living Room Door", "Living Room", "Empty", "Empty", "Empty", "Empty", "Observatory Door", "Observatory"},
                {"Green", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Clue", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
                {"Patio", "Patio", "Patio", "Patio", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Clue", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio Door", "Patio", "Patio", "Empty", "Empty", "Pool", "Pool", "Pool", "Pool", "Pool Door", "Pool", "Pool", "Pool", "Empty", "Hall", "Hall", "Hall", "Hall Door", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio Door", "Empty", "Empty", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Empty", "Hall", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Empty", "Empty", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Empty", "Hall Door", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Clue", "Empty", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Empty", "Hall Door", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Empty", "Empty", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Empty", "Hall", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio", "Patio Door", "Empty", "Empty", "Pool Door", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool", "Pool Door", "Empty", "Hall", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Patio", "Patio Door", "Patio", "Patio", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Clue", "Empty", "Empty", "Empty", "Empty", "Hall", "Hall", "Hall", "Hall", "Hall"},
                {"Patio", "Patio", "Patio", "Patio", "Clue", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Clue", "Empty", "Empty", "Empty"},
                {"White", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room Door", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
                {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Empty", "Empty", "Guest House Door", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Empty", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room Door", "Empty", "Empty", "Empty", "Empty", "Guest House Door", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen Door", "Empty", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Clue", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Clue", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Empty", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Empty", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"},
                {"Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Kitchen", "Mustard", "Empty", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Dining Room", "Empty", "Scarlet", "Empty", "Guest House", "Guest House", "Guest House", "Guest House"}
        };

        printNumberedBoard(board);
    }

    public static void printNumberedBoard(String[][] board) {
        // Mapping of room names to numbers
        Map<String, Integer> roomNumbers = new HashMap<>();
        int roomNumber = 1; // Start numbering rooms from 1
        int maxRoomNumberWidth = 1; // Initialize maximum room number width

        // Populate room numbers and find maximum room number width
        for (String[] row : board) {
            for (String room : row) {
                if (!room.equals("Empty") && !room.equals("Clue") && !roomNumbers.containsKey(room)) {
                    roomNumbers.put(room, roomNumber++);
                    maxRoomNumberWidth = Math.max(maxRoomNumberWidth, String.valueOf(roomNumber - 1).length());
                }
            }
        }

        // Print the board with room numbers
        for (String[] row : board) {
            for (int i = 0; i < row.length; i++) {
                String element = row[i];
                if (element.equals("Empty")) {
                    System.out.print("     "); // Six spaces for "Empty"
                } else if (element.equals("Clue")) {
                    System.out.print(" X   "); // Three spaces for "X"
                } else {
                    int roomNum = roomNumbers.get(element);
                    System.out.printf("%" + maxRoomNumberWidth + "d", roomNum); // Print room number with padding
                    System.out.print("   "); // Three spaces after room number
                }
            }
            System.out.println();
        }
    }
}
