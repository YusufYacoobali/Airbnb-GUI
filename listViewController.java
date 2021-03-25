import javafx.scene.control.Label;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.geometry.*;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.*;

public class listViewController implements Initializable {
    
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private String currentBorough;
    private AirbnbListing place;
    private Stage stage;
    private ArrayList<ShortPlace> placeView = new ArrayList<>();
    
    @FXML
    private ListView listView;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab initialTab;
    @FXML
    private ChoiceBox sort;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    
    @FXML
    public void setStage(String text, Stage stage){
        this.stage = stage;
        this.currentBorough = text;
        stage.setTitle(text);
        addHouses();
        
        sort.getItems().addAll("Price","Host name","Number of reviews");
    }
    
    @FXML
    private void addHouses(){
        for (AirbnbListing place: data.load()){
            if(place.getNeighbourhood().equals(currentBorough)){
                //create ShortPlace objects so that comparator can be used to sort them with ease for the listView.
                ShortPlace temp = new ShortPlace(place.getHost_name(), place.getPrice(), place.getMinimumNights(), place.getNumberOfReviews());
                placeView.add(temp);
                listView.getItems().add(temp.toString());
            }
        }
    }
    
    //i am adding sortList() method directly to fxml file as scenebuilder doesnt support it for choiceboxes
    @FXML
    private void sortList(){
        if (sort.getValue().equals("Host name")){
            Collections.sort(placeView, new SortByHost());            
            addSortedList();
        } else if (sort.getValue().equals("Price")) {
            Collections.sort(placeView, new SortByPrice());            
            addSortedList();
        } else if (sort.getValue().equals("Number of reviews")) {
            Collections.sort(placeView, new SortByReviews());
            addSortedList();
        } else {
            System.out.println("Invalid");
        }
    }
    
    private void addSortedList(){
        listView.getItems().clear();
        for (int i=0; i<placeView.size(); i++){
            listView.getItems().add(placeView.get(i).toString());
        }
    }

    @FXML
    private void listViewClicked(){
        for (AirbnbListing place: data.load()){
            String placeInfo = place.getHost_name() +  " hosting for $" + place.getPrice() + ", Min stay: " + place.getMinimumNights() + ", Number of reviews:" + place.getNumberOfReviews();
            if(placeInfo.equals(listView.getSelectionModel().getSelectedItem().toString())){
                this.place = place;
            }
        }
        Tab tab = new Tab(place.getRoom_type() + ": $" + place.getPrice());
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        tabs.getTabs().remove(initialTab);  
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(
            new Label(place.getId()),
            new Label("Hosted by:" + place.getHost_name()),
            new Label(),
            new Label(place.getRoom_type() + ": $" + place.getPrice()),
            new Label("Availability:" + place.getAvailability365()),
            new Label("Minimum nights:" + place.getMinimumNights()),
            new Label("Number of reviews:" + place.getNumberOfReviews()),
            new Label(),
            new Label("Location"),
            new Label(place.getNeighbourhood()),
            new Label("Latitude:" + place.getLatitude()),
            new Label("Longitude:" + place.getLongitude())
        );
        tab.setContent(vbox);
    }
}