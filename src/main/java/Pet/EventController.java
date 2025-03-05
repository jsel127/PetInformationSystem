package Pet;

import Database.dbConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller class to connect UI to database for the Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class EventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {

        myConnection = new dbConnection();
    }

    /**
     * Sets the PetID for the event
     * @param thePetID the key for the Pets table.
     */
    public void setPetID(int thePetID) {
        if (thePetID < 0) {
            throw new IndexOutOfBoundsException();
        }
        myPetID = thePetID;
    }
}
