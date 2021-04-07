
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class AccountHandlerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class AccountHandlerTest
{
    private AccountHandler account;
    private ApacheServer server;
    /**
     * Default constructor for test class AccountHandlerTest
     */
    public AccountHandlerTest()
    {
    }

    /**
     * Sets up the account object and Apache James server.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        account = new AccountHandler();
        server = new ApacheServer();
        AirbnbListing testPlace = new AirbnbListing("14535520", "Modern flat in Kingston", "10407160", "Andrew", "Kingston upon Thames", 51.40713375, -0.307181515, "Shared room",
        25, 2, 6, "19/02/2017", 1.22, 1, 83);

        account.rent(testPlace);
    }

    /**
     * Tears down the test fixture if required.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void testConnection()
    {
        assertTrue(server.isOnline());
    }
    
    @Test
    public void testRenting()
    {
        assertTrue(account.wasMailSent());
    }
}
