import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class which is responsible for showing the statistics on the GUI and control the buttons to navigate through them.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class StatisticViewer
{
    // instance variables - replace the example below with your own
    private StatisticEngine stat = new StatisticEngine(); // Creates a statistic engine class so the statistics can be accessed
    private HashMap<String, String> statistics = new HashMap<>(); // HashMap containg the title of the statistic and the statistic itself
    private ArrayList<String> shownStats = new ArrayList<>(); // ArrayList which will contain the statistics whicha re currently shown in one of the four blocks

    private int item = -1; //Initilalising the item index

    // The Label fields associated with the labels in the fxml file
    @FXML private Label topLeft; 
    @FXML private Label TLTitle;
    @FXML private Label bottomLeft;
    @FXML private Label BLTitle;
    @FXML private Label topRight;
    @FXML private Label TRTitle;
    @FXML private Label bottomRight;
    @FXML private Label BRTitle;

    /**
     * Constructor of the class
     * A call to a method which fills up the statistics hashMap with values on the initialization of a statistcViewr object.
     */
    public StatisticViewer(){
        fillStatisticMap();
     }

    /**
     * Fills in the hash map whcih maps the statistics with their titles.
     */
    private void fillStatisticMap(){
        statistics.put("Total number of available properties" , "" + stat.getTotalAvailableProperties());
        statistics.put("Average number of reviews per property", "" + stat.getAverageNumberOfreviewsPerProperty());
        statistics.put("Properties to book for less than a week", "" + stat.getBookForLessThanWeek());
        statistics.put("Number of entire home and apartments", "" + stat.getNumberOfEntireHomes());

        statistics.put("Most expensive borough", stat.getMostExpensiveBorough());
        statistics.put("Most Popular Borough", stat.getMostPopularBorough());
        statistics.put("Host with most properties", stat.getHostWithMostProperties());
        statistics.put("Number of Properties available all year", "" + stat.getNumberOfPropertiesAllYear());
    }

    /**
     * The method which connects to the forward button of the top left section of FXML file.
     * Passes the two labels as parameters 
     * Calls to two methods
     * @param event The action event (clicking the forward button)
     */
    @FXML
    private void TLForward(ActionEvent event){
        hideCurrent(TLTitle);
        forward(TLTitle, topLeft);
    }
    
    /**
     * Method for top left block's back button
     */
    @FXML
    private void TLBack(ActionEvent event){
        hideCurrent(TLTitle);
        back(TLTitle, topLeft);
    }

    /**
     * Method for top right block's forward button
     */
    @FXML
    private void TRForward(ActionEvent event){
        hideCurrent(TRTitle);
        forward(TRTitle, topRight);
    }  

    /**
     * Method for top right block's back button
     */
    @FXML
    private void TRBack(ActionEvent event){
        hideCurrent(TRTitle);
        back(TRTitle, topRight);
    } 

    /**
     * Method for bottom left block's forward button
     */
    @FXML
    private void BLForward(ActionEvent event){
        hideCurrent(BLTitle);
        forward(BLTitle, bottomLeft);
    }  

    /**
     * Method for bottom left block's back button
     */
    @FXML
    private void BLBack(ActionEvent event){
        hideCurrent(BLTitle);
        back(BLTitle, bottomLeft);
    } 

    /**
     * Method for bottom right block's forward button
     */
    @FXML
    private void BRForward(ActionEvent event){
        hideCurrent(BRTitle);
        forward(BRTitle, bottomRight);
    }  

    /**
     * Method for bottom right block's back button
     */
    @FXML
    private void BRBack(ActionEvent event){
        hideCurrent(BRTitle);
        back(BRTitle, bottomRight);
    } 

    /**
     * A search of the key set of the statistic map is carried out and if the title is found, a method responsible of 
     * removing the statistic from the Array list is invoked with the title being passed a s a parameter.
     * @param title The title of the statistic that needs to be unshown
     */
    private void hideCurrent(Label title){
        String titleText = title.getText();
        ArrayList<String> statTitles = new ArrayList<>(statistics.keySet());
        for(int i = 0; i < statTitles.size(); i++){
            if(titleText == statTitles.get(i)){    
                removeFromShown(titleText);
            }
        }
    }

    /**
     * Increments the item by one and changes the label in the GUI by showing the next statistic in the list.
     * If the item goes over the size of the list then the item is reset to zero
     * An if statement is used to check if the statistic is currently being shown. If it is then the item is incremented again and the method is called again reccursively with the same labels.
     * @param title The title of the statistic (Label)
     * @param stat The statistic itself (Label)
     */
    private void forward(Label title, Label stat){
        ArrayList<String> statisticTitles = new ArrayList<>(statistics.keySet()); // Arrraylist of the keys in the hashmap
        item++;
        if (item < statisticTitles.size()){ // check if the index item is in range
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){ //checks if the statistic is being shown or not
                title.setText(thisStatistic);
                stat.setText(statistics.get(thisStatistic));
                shownStats.add(thisStatistic);
            }
            else{
                forward(title, stat);
                return;
            }
        }

        else{
            item = 0;
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic);
                stat.setText(statistics.get(thisStatistic));
                shownStats.add(thisStatistic);
            }
            else{
                forward(title, stat);
                return;
            }
        }
        //System.out.println(item);
    }   

    /**
     * Decrements the item by one and changes the label in the GUI by showing the previous statistic in the list.
     * If the item gets smaller than zero, the item is changed to the last index of the statistics list.
     * An if statement is used to check if the statistic is currently being shown. If it is then the item is decremented again and the method is called again reccursively with the same labels.
     * @param title The title of the statistic (Label)
     * @param stat The statistic itself (Label)
     */
    private void back(Label title, Label stat){
        ArrayList<String> statisticTitles = new ArrayList<>(statistics.keySet());
        item--; 
        //System.out.println(item);

        if (item >= 0){
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic);
                stat.setText(statistics.get(thisStatistic));
                shownStats.add(thisStatistic);
            }
            else{
                back(title, stat);
                return;
            }
        }
        else{
            item = statisticTitles.size()-1 ;
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic);
                stat.setText(statistics.get(thisStatistic));
                shownStats.add(thisStatistic);
            }
            else{
                back(title, stat);
                return;
            }
        }
        //System.out.println(item);
    }    

    /**
     * A search is carried out of the shownStats Array list to check if the parameter is in it or not.
     * @return notShown - boolean which returns true if the statistic is not in the ArrayList
     * @param stat The statistic that is being checked
     */
    private boolean checkStatAvailability(String stat){
        boolean notShown = true;
        for(int i = 0; i < shownStats.size(); i++){
            if(shownStats.get(i) == stat){
                notShown = false;
            }
        }
        return notShown;
    }

    /**
     * A search is carried out of the shownStats ArrayList which finds the statistc passed as a parameter and removes it from the list.
     * @param The statistic that needs to be removed
     */
    private void removeFromShown(String stat){
        for(int i = 0; i < shownStats.size(); i++){
            if (shownStats.get(i) == stat){
                shownStats.remove(i);
            }
        }
    }
}
