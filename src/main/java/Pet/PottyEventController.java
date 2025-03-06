package Pet;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ObservableList<PottyEventData> myPottyData;

    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private TableView<PottyEventData> myPottyTable;
    @FXML
    private TableColumn<PottyEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<PottyEventData, String> myPottyTypeCol;
    @FXML
    private TableColumn<PottyEventData, String> myNotesCol;
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
     * Loads at most 50 events from the Potty Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadPottyEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myPottyData = FXCollections.observableArrayList();

            String query = "SELECT Potties.EventID, PottyTypes.PottyType, Potties.Notes " +
                           "FROM Potties JOIN PottyTypes ON Potties.PottyTypeID = PottyTypes.PottyTypeID " +
                           "JOIN EventLogs ON Potties.EventID = EventLogs.EventID " +
                           "WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myPottyData.add(new PottyEventData(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<PottyEventData, Integer>("myEventID"));
        myPottyTypeCol.setCellValueFactory(new PropertyValueFactory<PottyEventData, String>("myPottyType"));
        myNotesCol.setCellValueFactory(new PropertyValueFactory<PottyEventData, String>("myNotes"));
        myPottyTable.setItems(null);
        myPottyTable.setItems(myPottyData);
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

    /**
     * Returns to the pet page.
     * @param theEvent the triggering event
     */
    @FXML
    public void returnToEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myReturnEventPageBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/event.fxml").openStream());

            EventController eventController = (EventController) loader.getController();
            eventController.setPetID(myPetID);
            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
