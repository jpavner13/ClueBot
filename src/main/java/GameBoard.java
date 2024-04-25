import java.util.List;

// Singleton class.
public class GameBoard
{
    private static GameBoard instance;

    private String[][] board;

    public GameBoard()
    {
        this.board = new String[][]{
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
    }
    public static GameBoard getInstance()
    {
        if (instance == null)
        {
            instance = new GameBoard();
        }

        return instance;
    }

    public String[][] getBoard()
    {
        return board;
    }

    public String getTileName(int row, int col){
        return this.board[row][col];
    }

}