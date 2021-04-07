import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatisticEngineTest
{
    private StatisticEngine stat;

    /**
     * Default constructor for test class StatisticEngineTest
     */
    public StatisticEngineTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        stat = new StatisticEngine();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void testAverageReviewsPerProp()
    {
        assertEquals(12.0, stat.getAverageNumberOfreviewsPerProperty(), 0.1);
    }

    @Test
    public void testBookForLessThanWeek()
    {
        assertEquals(49865, stat.getBookForLessThanWeek());
    }

    @Test
    public void testHostWithMostProp()
    {
        assertEquals("Tom", stat.getHostWithMostProperties());
    }

    @Test
    public void testExpensiveBorough()
    {
        assertEquals("Westminster", stat.getMostExpensiveBorough());
    }

    @Test
    public void testPopularBorough()
    {
        assertEquals("Tower Hamlets", stat.getMostPopularBorough());
    }

    @Test
    public void testEntireHomes()
    {
        assertEquals(27175, stat.getNumberOfEntireHomes());
    }

    @Test
    public void testPropAllYear()
    {
        assertEquals(4619, stat.getNumberOfPropertiesAllYear());
    }

    @Test
    public void testAvailableProps()
    {
        assertEquals(41941, stat.getTotalAvailableProperties());
    }
}
