package Pet;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
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
 * @author Jasmine Sellers, Selly Beyene
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
    private ComboBox myPottyType;
    @FXML
    private TextArea myNotes;
    @FXML
    private Label myErrorMessage;
    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private BarChart<String, Integer> myBarChart;
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
        initializePottyTypeComboBox();
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
     * Initializes options for potty type combo box
     */
    private void initializePottyTypeComboBox() {
        String query = "SELECT PottyType FROM PottyTypes";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> pottyTypes = FXCollections.observableArrayList();
            while (rs.next()) {
                pottyTypes.add(rs.getString(1));
            }
            myPottyType.setItems(pottyTypes);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Adds an event into the potty table.
     * @param theEvent the triggering event.
     */
    @FXML
    public void addPotty(ActionEvent theEvent) {
        if (myPottyType.getValue() == null) {
            myErrorMessage.setText("Potty type is required.");
            return;
        }
        String insertExerciseStatement = "INSERT INTO Potties (EventID, PottyTypeID, Notes) VALUES (?, ?, ?);";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertEvent = conn.prepareStatement(insertExerciseStatement);
            prInsertEvent.setInt(1, myEventID);
            prInsertEvent.setInt(2, getPottyTypeID());
            prInsertEvent.setString(3, myNotes.getText());
            prInsertEvent.execute();
            conn.close();
            myErrorMessage.setText("Successfully added potty event.");
        } catch (SQLException ex) {
            myErrorMessage.setText("Only one entry of each event type per event is allowed.");
        }
    }

    /**
     * Gets the id of the potty type.
     * @return key of the selected potty type
     */
    @FXML
    private int getPottyTypeID() {
        String queryGetPottyTypeID = "SELECT PottyTypeID FROM PottyTypes WHERE PottyType = ?;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prPottiesType = conn.prepareStatement(queryGetPottyTypeID);
            prPottiesType.setString(1, myPottyType.getValue().toString());
            ResultSet rsPottiesType = prPottiesType.executeQuery();
            if (rsPottiesType.next()) {
                return rsPottiesType.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1;
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
