import java.net.URL;
import javafx.scene.Node;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.*; 
import javafx.scene.control.Label;
import javafx.application.Platform;

/**
 * Write a description of JavaFX class mapTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapMain extends Application
{   
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        URL url = getClass().getResource("map.fxml");
        Pane root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        stage.setTitle("Search By Borough");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }
}