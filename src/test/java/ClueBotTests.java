import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClueBotTests extends javax.swing.JFrame{
    @Test
    public void testClueBot() throws InterruptedException {
        DisplayWindowPage display = new DisplayWindowPage();

        while (display.frame.isVisible()) {
            Thread.sleep(100); // Sleep for a short interval to avoid CPU usage
        }
    }
}
