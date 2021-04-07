import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * AirbnbGUIController is the brain of the London Property Marketplace application.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class AirbnbGUIController implements Initializable
{
    // fields: 
    @FXML private Label currentRange;
    @FXML private Button leftButton;    
    @FXML private Button rightButton;
    @FXML private Pane secPane;
    @FXML private Spinner<Integer> lowerRange;
    @FXML private Spinner<Integer> upperRange;

    private String [] panes = {"welcome", "map", "statistic", "account"};
    private boolean defaultpage;    
    private int i;

    // static fields:
    private static int currentLower;
    private static int currentUpper;

    /**
     * Initializes the default values of components in the GUI.
     */
    @FXML 
    public void initialize(URL url, ResourceBundle resourceBundle){     
        try
        {
            loadFXML("welcome");
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
        defaultpage = true;
        currentRange.setText("Please select a price range...");

        //From this point we'll be creating the spinners for selecting the price range.
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
                    lowerRange.getValueFactory().setValue(upperRange.getValue() - 1);
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
                    lowerRange.getValueFactory().setValue(upperRange.getValue() - 1);
                }
                changeRange();
            }
        });
    }    
    
    /**
     * Go to the next panel in the list.
     */
    @FXML   
    private void nextPanel(ActionEvent event){
        if(i+2 <= panes.length){
            i++;
        }
        else{
            i = 0;
        }

        try
        {
            loadFXML(panes[i]);
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
    }    

    /**
     * Go to the previous panel in the list. 
     */
    @FXML    
    private void prevPanel(ActionEvent event){
        if(i == 0){
            i = panes.length - 1;
        }
        else{
            i--;
        } 

        try
        {
            loadFXML(panes[i]);
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Load the FXML file with the chosen name.
     * @param fxmlName Name of the FXML file.
     */
    @FXML
    private void loadFXML(String fxmlname) throws Exception {
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(fxmlname+ ".fxml"));
        secPane.getChildren().setAll(newLoadedPane);
    }

    /**
     * After the user changes the values of the range, check if there is a valid range 
     * and enable/disable the navigation buttons.
     */
    @FXML
    private void changeRange(){
        if(currentLower + currentUpper == 0){
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            currentRange.setText("Please select a price range...");
            if(!defaultpage){
                try
                {
                    loadFXML("welcome");
                }
                catch (java.lang.Exception e)
                {
                    e.printStackTrace();
                }
            }
            defaultpage = true;
        }
        else{
            leftButton.setDisable(false);
            rightButton.setDisable(false);
            currentRange.setText("Current range: £" +currentLower+ " - £" +currentUpper);
            defaultpage = false;
        }
    }

    /**
     * Returns the current lower range for price.
     * @return Price upper range value
     */
    public int getLowerRange(){
        return currentLower;   
    }

    /**
     * Returns the current upper range for price.
     * @return Price upper range value
     */
    public int getUpperRange(){
        return currentUpper;   
    }    
}
