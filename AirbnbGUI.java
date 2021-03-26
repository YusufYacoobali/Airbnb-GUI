import java.net.URL;
import javafx.scene.image.Image;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    @FXML 
    private ChoiceBox fromChoice;
    @FXML 
    private ChoiceBox toChoice;
    @FXML
    private Label currentRange;
    @FXML
    private Button leftButton;    
    @FXML
    private Button rightButton;
    @FXML
    private Pane secPane;
    
    private String [] panes = {"welcome", "statistic", "map", "panel4"};
    
    private int i;
    
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("resources/main.fxml");
        Pane root = FXMLLoader.load(url);

        Scene scene = new Scene (root);
        scene.getStylesheets().add("resources/mystyle.css");
        
        Image icon = new Image(getClass().getResourceAsStream("resources/icon.png"));
        
        stage.getIcons().add(icon); 
        
        stage.setTitle("Airbnb");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML   
    private void rightArrow(ActionEvent event) throws Exception
    {
        if(i+2 <= panes.length){
            i++;
        }
        else{
            i = 0;
        }
        
        loadFXML(panes[i]);
    }    
    
    @FXML    
    private void leftArrow(ActionEvent event) throws Exception
    {
        if(i == 0){
            i = panes.length - 1;
        }
        else{
            i--;
        } 
        
        loadFXML(panes[i]);
    }
    
    @FXML
    private Pane loadFXML(String fxmlname) throws Exception
    {
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("resources/" +fxmlname+ ".fxml"));
        secPane.getChildren().setAll(newLoadedPane);
        return newLoadedPane;
    }
    
    @FXML
    private void changeRange(ActionEvent event) throws Exception
    {
        String from = (String) fromChoice.getValue();
        String to = (String) toChoice.getValue();

        if(fromChoice.getValue() == "-" && toChoice.getValue() == "-"){
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            loadFXML("welcome");
        }
        else{
            leftButton.setDisable(false);
            rightButton.setDisable(false);
        }

        currentRange.setText("Current range: " +from+ " - " +to);
    }
    
    @FXML 
    private void initialize()
    {              
        fromChoice.getItems().addAll("-", "£50", "£151", "£351");
        fromChoice.getSelectionModel().select("-");
         
        toChoice.getItems().addAll("-", "£150", "£350", "£500");
        toChoice.getSelectionModel().select("-");
    }
        
    public static void main(String[] args) {
        launch(args);
    }
}
