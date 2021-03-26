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

public class MapController implements Initializable {
    
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private HashMap<String,Integer> boroughNumbers = new HashMap<>();
    
    @FXML
    private AnchorPane pane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setBoroughNumbers();
        colourCode();
    }
    
    //this opens another stage, the new stage has a seperate controller
    @FXML
     void buttonClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/listings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1)); 
            
            listViewController listViewController = fxmlLoader.getController();
            listViewController.setStage(((Button)event.getSource()).getText(), stage);
            
            //can only see one borough at a time
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
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
    
    private void setBoroughNumbers(){
        for (AirbnbListing place: data.load()){
            Integer occurance = boroughNumbers.get(place.getNeighbourhood().replaceAll("\\s",""));
            boroughNumbers.put(place.getNeighbourhood().replaceAll("\\s",""), (occurance == null) ? 1 : occurance + 1);
        }
    }
}