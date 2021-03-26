import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class StatisticViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticViewer
{
    // instance variables - replace the example below with your own
    private StatisticEngine stat;// = new StatisticEngine();
    private HashMap<String, String> statistics = new HashMap<>();
    private HashMap<String, Boolean> shown = new HashMap<>();
    private String currentStatistic;
    private int item;

    @FXML
    private Label topLeft;
    @FXML
    private Label TLTitle;
    @FXML
    private Label bottomLeft;
    @FXML
    private Label BLTitle;
    @FXML
    private Label topRight;
    @FXML
    private Label TRTitle;
    @FXML
    private Label bottomRight;
    @FXML
    private Label BRTitle;

    public StatisticViewer(){
        stat = new StatisticEngine();
        fillStatisticMap();
        fillShownMap();
    }

    private void fillStatisticMap(){
        statistics.put("Total number of available properties" , "" + stat.getTotalAvailableProperties());
        statistics.put("Average number of reviews per property", "" + stat.getAverageNumberOfreviewsPerProperty());
        statistics.put("Number of entire home and apartments", "" + stat.getNumberOfEntireHomes());
        statistics.put("Most expensive borough", stat.getMostExpeniveBourough());
    }

    private void fillShownMap(){
        shown.put("Total number of available properties", false);
        shown.put("Average number of reviews per property", false);
        shown.put("Number of entire home and apartments", false);
        shown.put("Most expensive borough", false);
    }

    @FXML
    private void TLForward(ActionEvent event){
        forward(TLTitle, topLeft);
    }

    @FXML
    private void TLBack(ActionEvent event){
        back(TLTitle, topLeft);
    }

    @FXML
    private void TRForward(ActionEvent event){
        forward(TRTitle, topRight);
    }  
    
    @FXML
    private void TRBack(ActionEvent event){
        back(TRTitle, topRight);
    } 
    

    private void forward(Label title, Label stat){
        String titleText = title.getText();
        shown.put(titleText, false);
        ArrayList<String> statisticTitles = new ArrayList<>(statistics.keySet());

        if (item < statisticTitles.size()){
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            else{
                item++;
                thisStatistic = statisticTitles.get(item);
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            item ++;
        }
        else if (item == statisticTitles.size()){
            item = 0;
            String thisStatistic = statisticTitles.get(item);
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            else{
                item++;
                thisStatistic = statisticTitles.get(item);
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            item ++;
        }

    }

    private void back(Label title, Label stat){
        String titleText = title.getText();
        shown.put(titleText, false);
        ArrayList<String> statisticTitles = new ArrayList<>(statistics.keySet());

        if (item > 0){
            item = item - 1;
            String thisStatistic = statisticTitles.get(item);
            
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            else{
                item--;
                thisStatistic = statisticTitles.get(item);
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
        }
        
        else if (item == 0){
            item = statisticTitles.size() - 1;
            String thisStatistic = statisticTitles.get(item);
            
            if(checkStatAvailability(thisStatistic)){
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);
            }
            else{
                item--;
                thisStatistic = statisticTitles.get(item);
                title.setText(thisStatistic); 
                stat.setText(statistics.get(thisStatistic));
                shown.put(title.getText(), true);  
            }
        }

    }

    private boolean checkStatAvailability(String stat){
        return (shown.get(stat) == false);
    }
}
