import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PlayerTests {
    @Test
    public void testInitializer (){
        int[] startingPos = {28, 18};
        String testPlayerName = "Scarlet";
        Player testPlayer = new Player(testPlayerName, startingPos);

        assertEquals(testPlayer.getPlayerName(), testPlayerName);
        assertEquals(testPlayer.getBoardPosition(), startingPos);

        int[] newPos = {18, 18};
        testPlayer.setBoardPosition(newPos);

        assertEquals(testPlayer.getBoardPosition(), newPos);
    }
}