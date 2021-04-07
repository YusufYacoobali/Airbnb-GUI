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
import java.util.*; 
import javafx.stage.Modality;

/**
 * This class is responsible for controlling all things to do with the map. 
 * From showing the map to handling the button clicks on each borough and also 
 * colour coding all the boroughs according to the number of listings they contain.
 * 
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class MapController implements Initializable 
{   
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private HashMap<String,Integer> boroughNumbers = new HashMap<>();
    
    @FXML private AnchorPane pane;
       
    /**
     * The system executes this method before anything else.
     * Two methods have to be run when this scene shows up.
     * They are the ones to set borough numbers and also to colour code the boroughs
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setBoroughNumbers();
        colourCode();
    }
    
    /**    
     * This method handles all the button clicks on each borough.
     * Whenever a borough is clicked, it displays all the listings in that particular borough in a new window.
     * This is done by getting the text on the button clicked
     *    
     * @param event ActionEvent that happens when any borough is clicked
     */
    @FXML
     void buttonClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1)); 
            
            listViewController listViewController = fxmlLoader.getController();
            listViewController.setStage(((Button)event.getSource()).getText(), stage);
            
            //can only see one borough at a time
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**    
     * All boroughs are colour-coded depending on the number of listings in that borough.
     * The id of the circles are used to get number of listings in that borough
     */
    @FXML
    private void colourCode(){
        for(Node node: pane.getChildren()){
            if (node instanceof Circle){
                Circle circle = (Circle) node;
                int occurance = boroughNumbers.get(circle.getId());
                if (occurance  < 500  ){
                    circle.setFill(Color.LIGHTGREEN);
                } else if (occurance < 1000){
                    circle.setFill(Color.LIGHTGREEN.darker());
                } else if (occurance < 1500){
                    circle.setFill(Color.LIGHTGREEN.darker().darker());
                } else {
                    circle.setFill(Color.LIGHTGREEN.darker().darker().darker());
                }
            }
        }
    }
    
    /**
     * This method sets the number of lisitings in each borough and puts them in a hashmap.
     * All white spaces are removed because circles cant have spaces in their fx:id's and the circles are used later with the hashmap
     */
    private void setBoroughNumbers(){
        for (AirbnbListing place: data.load()){
            Integer occurance = boroughNumbers.get(place.getNeighbourhood().replaceAll("\\s",""));
            boroughNumbers.put(place.getNeighbourhood().replaceAll("\\s",""), (occurance == null) ? 1 : occurance + 1);
        }
    }
}