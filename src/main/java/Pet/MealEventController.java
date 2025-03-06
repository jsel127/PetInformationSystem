package Pet;

import Database.dbConnection;
import Owner.OwnerController;
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
 * Controller class to connect UI to database for the Meal Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class MealEventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<MealEventData> myMealEventData;
    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private TableView<MealEventData> myMealTable;
    @FXML
    private TableColumn<MealEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<MealEventData, String> myMealTypeCol;
    @FXML
    private TableColumn<MealEventData, String> myNotesCol;

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
    /**
     * Loads at most 50 events from the Meal Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadMealEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myMealEventData = FXCollections.observableArrayList();

            String query = "SELECT Meals.EventID, MealTypes.MealType, Meals.Notes " +
                           "FROM Meals JOIN MealTypes ON Meals.MealTypeID = MealTypes.MealTypeID " +
                           "JOIN EventLogs ON Meals.EventID = EventLogs.EventID " +
                           "WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myMealEventData.add(new MealEventData(rs.getInt(1), rs.getString(2),
                                    rs.getString(3)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<MealEventData, Integer>("myEventID"));
        myMealTypeCol.setCellValueFactory(new PropertyValueFactory<MealEventData, String>("myMealType"));
        myNotesCol.setCellValueFactory(new PropertyValueFactory<MealEventData, String>("myNotes"));
        myMealTable.setItems(null);
        myMealTable.setItems(myMealEventData);
    }

}
