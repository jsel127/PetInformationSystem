package Pet;

import Database.dbConnection;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Potty Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class PottyEventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<PottyEventData> myPottyEventData;
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

    /**
     * Sets the EventID for the event
     * @param theEventID the key for the EventLogs table
     */
    public void setEventID(int theEventID) {
        if (theEventID < 0) {
            throw new IndexOutOfBoundsException();
        }
        myEventID = theEventID;
    }
}
