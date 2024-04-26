import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotTests
{

    @Test
    public void testMovementTargetSetAndGet1()
    {
        // Fake dice roll total.
        int roll1 = 2;
        int roll2 = 1;

        int[] startingPosition = {18, 28};
        int[] targetPosition = {18, 25};
        Bot bot = new Bot("bot", startingPosition);

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition[0], targetPosition[1]);

        bot.executeOptimalMovements(roll1);

        assertFalse(bot.isAtTarget());

        bot.executeOptimalMovements(roll2);

        assertTrue(bot.isAtTarget());
    }

    @Test
    public void testMovementTargetSetAndGet2()
    {
        // Fake dice roll total.
        int roll1 = 3;
        int roll2 = 99;

        int[] startingPosition = {18, 28};
        int[] targetPosition = {18, 25};
        Bot bot = new Bot("bot", startingPosition);

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition[0], targetPosition[1]);

        bot.executeOptimalMovements(roll1);

        assertTrue(bot.isAtTarget());

        bot.executeOptimalMovements(roll2);

        assertTrue(bot.isAtTarget());
    }

    @Test
    public void testMovementTargetSetAndGet3()
    {
        // As many moves as necessary to prove this point.
        int roll1 = 1000;

        int[] startingPosition = {18, 28};
        // Impossible target.
        int[] targetPosition = {-100, 25};
        Bot bot = new Bot("bot", startingPosition);

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition[0], targetPosition[1]);

        bot.executeOptimalMovements(roll1);

        assertFalse(bot.isAtTarget());
    }

    @Test
    public void testMovementTargetSetAndGet4()
    {
        // As many moves as necessary to prove this point.
        int roll1 = 1;

        int[] startingPosition = {18, 28};
        // Impossible target.
        int[] targetPosition = {18, 25};
        Bot bot = new Bot("bot", startingPosition);

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition[0], targetPosition[1]);

        bot.executeOptimalMovements(roll1);

        assertFalse(bot.isAtTarget());
    }

    @Test
    public void testMovementTargetSetAndGet5()
    {
        // As many moves as necessary to prove this point.
        int roll1 = 2;
        int roll2 = 2;

        int[] startingPosition = {18, 27};
        // Impossible target.
        int[] targetPosition1 = {18, 24};
        int[] targetPosition2 = {18, 27};

        Bot bot = new Bot("bot", startingPosition);

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition1[0], targetPosition1[1]);

        bot.executeOptimalMovements(roll1);

        assertFalse(bot.isAtTarget());

        bot.setMovementTarget(targetPosition2[0], targetPosition2[1]);

        bot.executeOptimalMovements(roll2);
        
        assertTrue(bot.isAtTarget());
    }

}