import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests
{
    @Test
    public void testGameBoardSingleton()
    {
        GameBoard board1 = GameBoard.getInstance();
        GameBoard board2 = GameBoard.getInstance();

        assertSame(board1, board2);
    }

    @Test
    public void testGameBoardgetBoard()
    {
        GameBoard board = GameBoard.getInstance();
        assertNotNull(board.getBoard());
    }

}