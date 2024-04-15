import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTests {
    @Test
    public void testInitializer(){
        int[] startingPos = {28, 18};
        String testPlayerName = "Scarlet";
        Player testPlayer = new Player(testPlayerName, startingPos);

        assertEquals(testPlayer.getPlayerName(), testPlayerName);
        assertEquals(testPlayer.getBoardPosition(), startingPos);

        int[] newPos = {18, 18};
        testPlayer.setBoardPosition(newPos);

        assertEquals(testPlayer.getBoardPosition(), newPos);
    }

    @Test
    public void testAdjacentMoves()
    {
        int[] startingPos = {28, 18};
        String testPlayerName = "Scarlet";
        Player testPlayer = new Player(testPlayerName, startingPos);

        int[] move1 = {28, 17};
        int[] move2 = {27, 18};
        int[] move3 = {28, 19};
        for (int[] move : testPlayer.getAdjacentMoves())
        {
            assertTrue((Arrays.equals(move, move1)) || (Arrays.equals(move, move2)) || (Arrays.equals(move, move3)));
        }

        assertTrue(true);
    }
}