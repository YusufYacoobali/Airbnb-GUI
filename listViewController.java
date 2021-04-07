import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URI;
import java.net.URL;
import java.util.*;
import javafx.geometry.*;

/**
 * This class controls the the window that pops up when a borough on the map is clicked.
 * A list view of all the listings is displayed along with a tab pane on the side.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class listViewController implements Initializable {    
    private AirbnbDataLoader data = new AirbnbDataLoader();
    private AirbnbGUIController guicontroller = new AirbnbGUIController();
    private AirbnbListing place;
    private AccountHandler account = new AccountHandler();
    private Stage stage;
    
    // fields:
    @FXML private ListView listView;
    @FXML private TabPane tabs;
    @FXML private Tab initialTab;
    @FXML private ChoiceBox sort;
    @FXML private Label displayName;
    @FXML private Label displayRange;
    private Button lookOnMap = new Button("Check on Map");
    private Button rent = new Button("Click to rent");
    private int count;
    private String currentBorough;
    private ArrayList<ShortPlace> placeView = new ArrayList<>();
    
    /**
     * Nothing is required beforehand to show this scene.    
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    
    /**
     * Every time a button is pressed on the map a new stage is created and so it has to be set.
     * The houses of that borough are set along with the title and sort feature.
     * @param text Borough name from button text
     * @param stage The newly created stage when the button is pressed
     */
    @FXML
    public void setStage(String text, Stage stage){
        this.stage = stage;
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        
        stage.getIcons().add(icon);
        this.currentBorough = text;
        stage.setTitle(text);
        displayName.setText("Property viewer for " +text);
        displayRange.setText("Current price range: £" +guicontroller.getLowerRange()+ " - £" +guicontroller.getUpperRange());
        addHouses();
        
        sort.getItems().addAll("Price","Host name","Number of reviews");
    }
    
    /**
     * This method checks every listing in the file and shows only the ones with the same borough and in the price range.
     */
    @FXML
    private void addHouses(){
        for (AirbnbListing place: data.load()){
            if(place.getNeighbourhood().equals(currentBorough) && inRange(place)){
                //create ShortPlace objects so that comparator can be used to sort them with ease for the listView.
                ShortPlace temp = new ShortPlace(place.getHost_name(), place.getPrice(), place.getMinimumNights(), place.getNumberOfReviews());
                placeView.add(temp);
                listView.getItems().add(temp.toString());
                count++;
            }
        }
        
        if(count == 0){
            listView.getItems().add("No properties at this price range.");    
        }
    }
    
    /**
     * This method sorts the arraylist using the collections and classes created specifically to sort the list by separate fields.
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
     * Clears the current list view and add the newly sorted list.
     */
    private void addSortedList(){
        if(count > 0){
            listView.getItems().clear();
            for (int i=0; i<placeView.size(); i++){
                listView.getItems().add(placeView.get(i).toString());
            }
        }
    }

    /**
     * This method gets the listing which the user selects and then calls the createTab method on that listing.
     */
    @FXML
    private void listViewClicked(){
        for (AirbnbListing place: data.load()){
            String placeInfo = place.getHost_name() +  " hosting for $" + place.getPrice() + ", Min stay: " + place.getMinimumNights() + ", Number of reviews:" + place.getNumberOfReviews();
            if(listView.getSelectionModel().getSelectedItem() != null && placeInfo.equals(listView.getSelectionModel().getSelectedItem().toString())){
                this.place = place;
            }
        }
        
        if(listView.getSelectionModel().getSelectedItem() != null && count > 0){
            createTab(place);
        }
    }
    
    /**
     * A new tab is created to display the info of the selected listing.
     * @param place, the listing which the user clicked to see more info of.
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
            new Label("Longitude:" + place.getLongitude()),
            lookOnMap,
            rent
        );
        
        lookOnMap.setOnAction(ev ->
        {
            try
            {
                 showOnMap(place);
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace();
            }
        });
        
        rent.setOnAction(ev -> rent(place));       
    
        tab.setContent(vbox);
    }
    
    /**
     * This method is used to check if a listing is in the selected price range.
     * @param place The specific listing
     * @return True if the property is within the range
     */
    private boolean inRange(AirbnbListing place){
        return place.getPrice() <= guicontroller.getUpperRange() && place.getPrice() >= guicontroller.getLowerRange();
    }
    
    /**
     * Takes the latitude and longitude of the property that is currently selected and displays
     * it on Google maps.
     * @param place the listing the user is looking at
     */
    private void showOnMap(AirbnbListing place){
       try{
               double latitude = place.getLatitude();
               double longitude = place.getLongitude();
             
               URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
               java.awt.Desktop.getDesktop().browse(uri);
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace();
            }
    }
    
    /**
     * Method called when renting a property.
     * Place object passed as parameter to assemble
     * information into email.
     */
    private void rent(AirbnbListing place){
        if (account.emailExists()) {
            account.rent(place);
        }
        else {
            System.out.println("Please register first");
        }
    }
}