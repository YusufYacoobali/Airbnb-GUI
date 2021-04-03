import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Write a description of class AirbnbGUIController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbGUIController
{
    @FXML
    private Label currentRange;
    @FXML
    private Button leftButton;    
    @FXML
    private Button rightButton;
    @FXML
    private Pane secPane;
    @FXML 
    private Spinner<Integer> lowerRange;
    @FXML 
    private Spinner<Integer> upperRange;
    
    private int currentLower;
    private int currentUpper;
    
    private String [] panes = {"welcome", "statistic", "map", "panel4"};
    
    private int i;

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
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(fxmlname+ ".fxml"));
        secPane.getChildren().setAll(newLoadedPane);
        return newLoadedPane;
    }
    
    @FXML
    private void changeRange()
    {
        if(currentLower + currentUpper == 0){
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            currentRange.setText("Please select a price range...");
            //loadFXML("welcome");
        }
        else{
            leftButton.setDisable(false);
            rightButton.setDisable(false);
            currentRange.setText("Current range: £" +currentLower+ " - £" +currentUpper);
        }
    }
    
    @FXML 
    private void initialize()
    {     
        currentRange.setText("Please select a price range...");
        
        //From this point we'll be creating the spinners for the price range.
        SpinnerValueFactory<Integer> HigherValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8000,0);
        upperRange.setValueFactory(HigherValueFactory);
        
        SpinnerValueFactory<Integer> LowerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8000,0);
        lowerRange.setValueFactory(LowerValueFactory);
        
        lowerRange.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) 
            {    
                currentLower = lowerRange.getValue();        
                if (upperRange.getValue() <  lowerRange.getValue()){
                    lowerRange.getValueFactory().setValue(0);
                }
                changeRange();
            }
        });
    
        upperRange.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) 
            {    
                currentUpper = upperRange.getValue();        
                if (upperRange.getValue() <  lowerRange.getValue()){
                    lowerRange.getValueFactory().setValue(0);
                }
                changeRange();
            }
        });
    }
    
    private int getLowerRange()
    {
        return currentLower;   
    }
    
    private int getUpperRange()
    {
        return currentUpper;   
    }
}
