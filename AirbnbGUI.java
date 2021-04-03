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
 * Write a description of JavaFX class AirbnbGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbGUI extends Application
{    
    /**
     * The start method is the main entry point for every JavaFX application.
     * It is called after the init() method has returned and after
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("temp.fxml");
        Pane root = FXMLLoader.load(url);
 
        Scene scene = new Scene (root);
        scene.getStylesheets().add("mystyle.css");
       
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
       
        stage.getIcons().add(icon);
       
        stage.setTitle("Airbnb");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
     
    public static void main(String[] args) {
        launch(args);
    }
}