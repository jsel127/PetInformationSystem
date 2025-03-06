package Pet;

import Database.dbConnection;
import Owner.OwnerController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Medical Checkup Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class MedicalCheckupEventController implements Initializable{
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<MedicalCheckupEventData> myMedicalCheckupEventData;
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
    }
}
