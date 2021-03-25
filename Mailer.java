import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.*;

/**
 * Write a description of JavaFX class Mailer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mailer extends Application
{
    // We keep track of the count, and label displaying the count:
    int currentPropertyInt = 0;

    String emailFieldText = "enter email";
    TextField emailField = new TextField(emailFieldText);

    AirbnbDataLoader loader = new AirbnbDataLoader();
    ArrayList<AirbnbListing> propertyList = loader.load();

    Label labelID = new Label("ID: " + propertyList.get(currentPropertyInt).getId());
    Label labelName = new Label("Name: " + propertyList.get(currentPropertyInt).getName());
    Label labelHostID = new Label("Host ID: " + propertyList.get(currentPropertyInt).getHost_id());
    Label labelHostName = new Label("Host Name: " + propertyList.get(currentPropertyInt).getHost_name());
    Label labelNeighbourhood = new Label("Neighbourhood: " + propertyList.get(currentPropertyInt).getNeighbourhood());
    Label labelLatitude = new Label("Latitude: " + propertyList.get(currentPropertyInt).getLatitude());
    Label labelLongitude = new Label("Longitude: " + propertyList.get(currentPropertyInt).getLongitude());
    Label labelRoomType = new Label("Room Type: " + propertyList.get(currentPropertyInt).getRoom_type());
    Label labelPrice = new Label("Price: " + propertyList.get(currentPropertyInt).getPrice());
    Label labelMinimumNights = new Label("Minimum Nights: " + propertyList.get(currentPropertyInt).getMinimumNights());
    Label labelNumberOfReviews = new Label("Number of Reviews: " + propertyList.get(currentPropertyInt).getNumberOfReviews());
    Label labelLastReview = new Label("Last Review: " + propertyList.get(currentPropertyInt).getLastReview());
    Label labelReviewsPerMonth = new Label("Reviews Per Month: " + propertyList.get(currentPropertyInt).getReviewsPerMonth());
    Label labelCalculatedHostListingsCount = new Label("Calculated Host Listings Count: " + propertyList.get(currentPropertyInt).getCalculatedHostListingsCount());
    Label labelAvailability365 = new Label("Availability/365: " + propertyList.get(currentPropertyInt).getAvailability365());
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        // Create a Button or any control item
        Button buttonPrevious = new Button();
        Button buttonNext = new Button();
        Button buttonRent = new Button("rent");

        SplitPane splitPane = new SplitPane(emailField, buttonRent);

        // Create a new border pane
        BorderPane root = new BorderPane();
        VBox propertyPane = new VBox();
        propertyPane.getChildren().addAll(labelID, labelName, labelHostID, labelHostName, labelNeighbourhood, labelLatitude, labelLongitude, labelRoomType, labelPrice, labelMinimumNights, 
            labelNumberOfReviews, labelLastReview, labelReviewsPerMonth, labelCalculatedHostListingsCount, labelAvailability365);

        //set an action on the button using method reference
        buttonPrevious.setOnAction(this::showPrevious);
        buttonNext.setOnAction(this::showNext);
        buttonRent.setOnAction(this::sendMail);
        //emailField.setOnMouseEntered(this::emailFieldHide);
        //emailField.setOnMouseExited(this::emailFieldShow);

        // Add the button and label into the pane
        root.setCenter(propertyPane);
        root.setLeft(buttonPrevious);
        root.setRight(buttonNext);
        root.setBottom(splitPane);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void showPrevious(ActionEvent event)
    {
        currentPropertyInt = (currentPropertyInt - 1);
        if (currentPropertyInt < 0)
        {
            currentPropertyInt = propertyList.size();
        }
        System.out.println("" + currentPropertyInt);
    }

    private void showNext(ActionEvent event)
    {
        currentPropertyInt = (currentPropertyInt + 1)%(propertyList.size()+1);
        System.out.println("" + currentPropertyInt);
    }

    private void sendMail(ActionEvent event)
    {
        try
        {
            MimeMessage message =  new MimeMessage(Session.getDefaultInstance(System.getProperties()));
            message.setFrom(new InternetAddress("MailerService@gmail.com"));
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailField.getText()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailField.getText()));
            message.setSubject("Airbnb property has been rented");
            message.setText("You have rented property with ID " + propertyList.get(currentPropertyInt).getId() + ", titled '" + propertyList.get(currentPropertyInt).getName() + "'");
            Transport.send(message);
            System.out.println("success");
        }
        catch (MessagingException mex)
        {
            System.out.println("failure");
            mex.printStackTrace();
        }
    }

    private void emailFieldHide(MouseEvent event)
    {
        emailField.setText("");
    }

    private void emailFieldShow(MouseEvent event)
    {
        emailField.setText(emailFieldText);
    }
}
