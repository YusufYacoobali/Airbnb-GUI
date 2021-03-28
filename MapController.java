import javafx.event.ActionEvent;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.*; 
import javafx.stage.Modality;

/**
 * This class is the controller for the map.
 * It colours each borough according to the number of listings
 * Also it handles the button clicks on each borough
 *
 * @author Yusuf Yacoobali
 * @version v1
 */
public class MapController implements Initializable {
    
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private HashMap<String,Integer> boroughNumbers = new HashMap<>();
    
    //the anchor pane variable here is linked with the one from the fxml file
    @FXML
    private AnchorPane pane;
    
    /**
     * This is like a constructor, the system executes this method before anything else
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setBoroughNumbers();
        colourCode();
    }
    
    /**
     * This method handles all the button clicks on each borough
     * Whenever a borough is clicked, it displays all the listings in that particular borough in a new window
     * This is done by getting the text on the button clicked
     *
     * @param ActionEvent that happens when any borough is clicked
     */
    @FXML
    public void buttonClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/listings.fxml"));
            Parent listFile = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(listFile)); 
            
            listViewController listViewController = fxmlLoader.getController();
            listViewController.setStage(((Button)event.getSource()).getText(), stage);
            
            //can only see one borough at a time
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * All boroughs are colour coded depending on the number of listings in that borough
     * the id of the circles are used to get number of listings in that borough
     */
    @FXML
    private void colourCode(){
        for(Node node: pane.getChildren()){
            if (node instanceof Circle){
                Circle circle = (Circle) node;
                int occurance = boroughNumbers.get(circle.getId());
                if (occurance  < 500  ){
                    circle.setFill(Color.BLUE);
                    circle.setOpacity(0.3);
                } else if (occurance < 1000){
                    circle.setFill(Color.ORANGE);
                    circle.setOpacity(0.4);
                } else if (occurance < 1500){
                    circle.setFill(Color.ORANGERED);
                    circle.setOpacity(0.6);
                } else {
                    circle.setFill(Color.RED);
                    circle.setOpacity(0.9);
                }
            }
        }
    }
    
    /**
     * This method sets the number of lisitings in each borough and puts them in a hashmap
     * all white spaces are removed because circles cant have spaces in their fx:id's and the circles are used later with the hashmap
     */
    private void setBoroughNumbers(){
        for (AirbnbListing place: data.load()){
            Integer occurance = boroughNumbers.get(place.getNeighbourhood().replaceAll("\\s",""));
            boroughNumbers.put(place.getNeighbourhood().replaceAll("\\s",""), (occurance == null) ? 1 : occurance + 1);
        }
    }
}