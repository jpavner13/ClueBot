import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTests {
    @Test
    public void testCard(){
        String CardName = "Rope";
        CardTypes type = CardTypes.WeaponCard;
        Card testCard = new Card(CardName, type);

        assertEquals(testCard.getCardName(), CardName);
        assertEquals(testCard.getTypeOfCard(), type);
    }
}
