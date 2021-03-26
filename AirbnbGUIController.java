import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;

/**
 * Write a description of class AirbnbGUIController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbGUIController
{
    @FXML private ChoiceBox fromChoice;
    @FXML private ChoiceBox toChoice;
    @FXML private Label currentRange;
    @FXML private Button leftButton;    
    @FXML private Button rightButton;
    @FXML private BorderPane mainPane;

    /**
     * Constructor for objects of class AirbnbGUIController
     */
    public AirbnbGUIController()
    {
        // initialise instance variables
    }

    @FXML   
    private void rightArrow(ActionEvent event)
    {
       System.out.println("Right");
    }
    
    @FXML    
    private void leftArrow(ActionEvent event)
    {
       System.out.println("Left");
    }
    
    @FXML
    private void changeRange(ActionEvent event)
    {
        String from = (String) fromChoice.getValue();
        String to = (String) toChoice.getValue();
        
        if(fromChoice.getValue() == "-"){
            from = "";
        }
        if(toChoice.getValue() == "-"){
            to = "";
        }
        
        if(to.equals("") && from.equals("")){
            leftButton.setDisable(true);
            rightButton.setDisable(true);
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
}
