import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClueBotTests extends javax.swing.JFrame{
    @Test
    public void testClueBot() throws InterruptedException, MalformedURLException, PropertyVetoException {
        DisplayWindowPage display = new DisplayWindowPage();

        display.addCardToHand("Pool");
        display.addCardToHand("Hall");
        display.addCardToHand("Rope");
        display.addCardToHand("Miss Scarlett");

        while (display.frame.isVisible()) {
            Thread.sleep(100); // Sleep for a short interval to avoid CPU usage
        }
    }
}
