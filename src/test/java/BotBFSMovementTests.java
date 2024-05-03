import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotBFSMovementTests {
    @Test
    public void testMovementTargetSetAndGet1()
    {
        // Fake dice roll total.
        int roll1 = 2;
        int roll2 = 1;

        int[] startingPosition = {28, 18};
        int[] targetPosition = {25, 18};
        Bot bot = new Bot("bot", startingPosition, new BotAllAvailableInfoDeductionStrategy());

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

        int[] startingPosition = {28, 18};
        int[] targetPosition = {25, 18};
        Bot bot = new Bot("bot", startingPosition, new BotAllAvailableInfoDeductionStrategy());

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

        int[] startingPosition = {28, 18};
        // Impossible target.
        int[] targetPosition = {25, -100};
        Bot bot = new Bot("bot", startingPosition, new BotAllAvailableInfoDeductionStrategy());

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

        int[] startingPosition = {28, 18};
        // Impossible target.
        int[] targetPosition = {25, 18};
        Bot bot = new Bot("bot", startingPosition, new BotAllAvailableInfoDeductionStrategy());

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

        int[] startingPosition = {27, 18};
        // Impossible target.
        int[] targetPosition1 = {24, 18};
        int[] targetPosition2 = {27, 18};

        Bot bot = new Bot("bot", startingPosition, new BotAllAvailableInfoDeductionStrategy());

        // Simulate 3 optimal moves
        bot.setMovementTarget(targetPosition1[0], targetPosition1[1]);

        bot.executeOptimalMovements(roll1);

        assertFalse(bot.isAtTarget());

        bot.setMovementTarget(targetPosition2[0], targetPosition2[1]);

        bot.executeOptimalMovements(roll2);

        assertTrue(bot.isAtTarget());
    }
}
