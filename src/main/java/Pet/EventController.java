package Pet;

import Database.dbConnection;
import Owner.OwnerController;
import Owner.OwnershipData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    /** Stores loaded events */
    private ObservableList<EventData> myEventData;

    @FXML
    private DatePicker myStartDateTime;
    @FXML
    private DatePicker myEndDateTime;
    @FXML
    private Button myPottyEventBtn;
    @FXML
    private Button myMealEventBtn;
    @FXML
    private Button myExerciseEventBtn;
    @FXML
    private Button myTrainingEventBtn;
    @FXML
    private Button myGroomingEventBtn;
    @FXML
    private Button myMedicalCheckupEventBtn;
    @FXML
    private Button myReturnPetPageBtn;
    @FXML
    private TableView<EventData> myEventTable;
    @FXML
    private TableColumn<EventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<EventData, String> myStartDateTimeCol;
    @FXML
    private TableColumn<EventData, String> myEndDateTimeCol;

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
     * Loads at most 50 events from the Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myEventData = FXCollections.observableArrayList();

            String query = "SELECT EventID, StartDateTime, EndDateTime FROM EventLogs WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myEventData.add(new EventData(rs.getInt(1), rs.getString(2),
                                rs.getString(3)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<EventData, Integer>("myEventID"));
        myStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<EventData, String>("myStartDateTime"));
        myEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<EventData, String>("myEndDateTime"));

        myEventTable.setItems(null);
        myEventTable.setItems(myEventData);
    }

    @FXML
    public void returnToPetDashboard(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myReturnPetPageBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/pet.fxml").openStream());

            OwnerController ownerController = (OwnerController) loader.getController();
            ownerController.setUserID(myPetID);
            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Pet Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
