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

/**
 * This class controls the the window that pops up when a borough on the map is clicked
 * A list view of all the listings is displayed along with a tab pane on the side
 *
 * @author Yusuf Yacoobali
 * @version v1
 */
public class listViewController implements Initializable {
    
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private String currentBorough;
    private AirbnbListing place;
    private Stage stage;
    private ArrayList<ShortPlace> placeView = new ArrayList<>();
    
    //These variables are linked with the ones from the fxml file
    @FXML
    private ListView listView;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab initialTab;
    @FXML
    private ChoiceBox sort;
    
    /**
     * This is like a constructor, the system executes this method before anything else
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    
    /**
     * Every time a button is pressed a new stage is created and so it has to be set
     * The houses of that borough are set along with the title and sort feature
     * @param borough name from button text
     * @param the newly created stage when the button is pressed
     */
    @FXML
    public void setStage(String text, Stage stage){
        this.stage = stage;
        this.currentBorough = text;
        stage.setTitle(text);
        addHouses();
        
        sort.getItems().addAll("Price","Host name","Number of reviews");
    }
    
    /**
     * This method checks every listing in the file and shows only the ones with the same borough and in the price range
     */
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
    
    /**
     * This method is used to check if a listing is in the desired price range
     */
    // private boolean inRange(AirbnbListing place){
        // return place.getPrice() < maxPrice && place.getPrice() > minPrice;
    // }
    
    /**
     * This method sorts the arraylist using the collections and classes created specifically to sort the list by seperate fields
     */
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
    
    /**
     * Clears the current list view and add the newly sorted list
     */
    private void addSortedList(){
        listView.getItems().clear();
        for (int i=0; i<placeView.size(); i++){
            listView.getItems().add(placeView.get(i).toString());
        }
    }
    
    /**
     * This method gets the listing which the user selects and then calls the createTab method on that listing
     */
    @FXML
    private void listViewClicked(){
        for (AirbnbListing place: data.load()){
            String placeInfo = place.getHost_name() +  " hosting for $" + place.getPrice() + ", Min stay: " + place.getMinimumNights() + ", Number of reviews:" + place.getNumberOfReviews();
            if(placeInfo.equals(listView.getSelectionModel().getSelectedItem().toString())){
                this.place = place;
            }
        }
        createTab(place);
    }
    
    /**
     * A new tab is created to display the info of the selected listing
     */
    private void createTab(AirbnbListing place){
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