import java.net.URL;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * AirbnbGUI is the main class of the London Property Marketplace application. It builds
 * and displays the GUI.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class AirbnbGUI extends Application
{
    /**
     * Main function, launches GUI builder.
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * The main entry point for the London Property Marketplace application. 
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        ApacheServer server = new ApacheServer();
        
        URL url = getClass().getResource("main.fxml");
        Pane root = FXMLLoader.load(url);

        Scene scene = new Scene (root);
        scene.getStylesheets().add("mystyle.css");
        
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        
        stage.getIcons().add(icon); 
        
        stage.setTitle("London Property Marketplace");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
