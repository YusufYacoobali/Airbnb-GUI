import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;

/**
 * Account handler class to manage user's details and send emails when renting.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class AccountHandler
{
    @FXML private TextField email;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private Label warningLabel;
    private Boolean mailSent = false;
    static private Boolean emailExists = false;
    static String gmail = "kamilduszak016@gmail.com"; //default values needed for test
    static String firstName = "Kamil";
    static String lastName = "Duszak";

    /**
     * Constructor for objects of class AccountHandler
     */
    public AccountHandler()
    {

    }

    /**
     * When 'Register' button is clicked, the text
     * stored in the three fields is saved here.
     */

    @FXML
    private void register(){
        gmail = email.getText();
        if (gmail.contains("@gmail.com")) {
            gmail = email.getText();
            emailExists = true;
            warningLabel.setText("Registration successful.");
            warningLabel.setTextFill(Color.GREEN);
        } else {
            warningLabel.setText("Must enter a valid Gmail account");
            emailExists = false;
            warningLabel.setTextFill(Color.RED);
        }
        firstName = firstname.getText();
        lastName = lastname.getText();
    }

    /**
     * When 'Rent' button is clicked in the list viewer,
     * this method gets called to send an email to the
     * registered gmail with relevant info.
     */

    public void rent(AirbnbListing place)
    {
        System.out.println("receiver: " + gmail);
        try
        {
            MimeMessage message =  new MimeMessage(Session.getDefaultInstance(System.getProperties()));
            message.setFrom(new InternetAddress("MailerService@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(gmail));
            message.setSubject(firstName + " " + lastName + ", your Airbnb property has been rented");

            //if this line is to be used, it will require data about the properties
            message.setText("You have rented property with ID " + place.getId() + ", titled " + place.getName() + " for £" + place.getPrice());

            Transport.send(message);
            System.out.println("success \n   (check spam)");
            mailSent = true;
        }
        catch (MessagingException mex)
        {
            System.out.println("failure");
            mex.printStackTrace();
        }
    }    
    
    /**
     * Returns true if an email has been sent at least once.
     */
    
    public Boolean wasMailSent()
    {
        return mailSent;
    }
    
    /**
     * Returns true if a valid email (@gmail.com) is being stored in the gmail String.
     */
    
    public Boolean emailExists()
    {
        return emailExists;
    }
}
