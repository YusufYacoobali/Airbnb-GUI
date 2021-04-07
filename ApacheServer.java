import java.io.File;

/**
 * Class responsible for initialising the Apache James server at startup.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class ApacheServer
{
    private Boolean isOnline = false;
    /**
     * Constructor for objects of class ApacheServer
     * Starts server to establish internet connection for mail service.
     */
    public ApacheServer()
    {
        startServer();
    }

    /**
     * Method to initliase Apache James server to allow for mail transfer.
     */

    private void startServer()
    {
        ProcessBuilder server = new ProcessBuilder("cmd", "/c", "run.bat");
        File directory = new File(System.getProperty("user.dir") + "\\james server\\bin");
        server.directory(directory);
        try
        {
            Process process = server.start();
            System.out.println("connection established");
            isOnline = true;
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Checks connection of server for testing purposes.
     */
    
    public Boolean isOnline()
    {
        return isOnline;
    }
}
