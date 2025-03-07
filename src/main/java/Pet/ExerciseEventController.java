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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Exercise Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class ExerciseEventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    @FXML
    private Label myErrorMessage;
    /** Bar chart to track the event types */
    @FXML
    private BarChart<String, Integer> myBarChart;
    @FXML
    private TableView<ExerciseEventData> myExerciseTable;
    @FXML
    private TableColumn<ExerciseEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<ExerciseEventData, String> myExerciseTypeCol;
    @FXML
    private TableColumn<ExerciseEventData, Integer> myIntensityLevelCol;
    @FXML
    private TableColumn<ExerciseEventData, String> myDistanceCol;
    @FXML
    private ComboBox myExerciseType;
    @FXML
    private TextField myIntensityLevel;
    @FXML
    private TextField myDistance;
    /***/
    @FXML
    private NumberAxis myYAxis;
    @FXML
    private CategoryAxis myXAxis;
    @FXML
    private Button myReturnEventPageBtn;
    private ObservableList<ExerciseEventData> myExerciseData;

    /** Stores loaded events */
    private ObservableList<ExerciseEventReportData> myExerciseEventData;

    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
        initializeExerciseTypeComboBox();
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
     * @param theEventID the eventID
     */
    public void setEventID(int theEventID) {
        if (theEventID < 0) {
            throw new IndexOutOfBoundsException();
        }
        myEventID = theEventID;
    }

    private void initializeExerciseTypeComboBox() {
        String query = "SELECT ExerciseName FROM ExerciseTypes";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> exerciseNames = FXCollections.observableArrayList();
            while (rs.next()) {
                exerciseNames.add(rs.getString(1));
            }
            myExerciseType.setItems(exerciseNames);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds an event into the exercise table.
     * @param theEvent the triggering event.
     */
    @FXML
    public void addExercise(ActionEvent theEvent) {
        String insertExerciseStatement = "INSERT INTO Exercises (EventID, ExerciseTypeID, IntensityLevel, Distance) VALUES (?, ?, ?, ?);";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertEvent = conn.prepareStatement(insertExerciseStatement);
            prInsertEvent.setInt(1, myEventID);
            prInsertEvent.setInt(2, getExerciseTypeID());
            prInsertEvent.setInt(3, Integer.parseInt(myIntensityLevel.getText().toString()));
            prInsertEvent.setString(4, myDistance.getText());
            prInsertEvent.execute();
            conn.close();
            myErrorMessage.setText("Sucessfully added exercise event.");
        } catch (SQLException ex) {
            myErrorMessage.setText("Only one entry of each event type per event is allowed.");
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Gets the id related to the exercise type.
     * @return the ID fo the exercise type in the ExerciseTypes table.
     */
    private int getExerciseTypeID() {
        String queryGetGroomingTypeID = "SELECT ExerciseTypeID FROM ExerciseTypes WHERE ExerciseName = ?;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prGroomingType = conn.prepareStatement(queryGetGroomingTypeID);
            prGroomingType.setString(1, myExerciseType.getValue().toString());
            ResultSet rsGroomingType = prGroomingType.executeQuery();
            if (rsGroomingType.next()) {
                return rsGroomingType.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1;
    }
    /**
     * Loads at most 50 events from the Exercise Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadExerciseEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myExerciseData = FXCollections.observableArrayList();

            String query = "SELECT Exercises.EventID, ExerciseTypes.ExerciseName, IntensityLevel, Distance " +
                           "FROM Exercises JOIN ExerciseTypes ON Exercises.ExerciseTypeID = ExerciseTypes.ExerciseTypeID " +
                           "JOIN EventLogs ON Exercises.EventID = EventLogs.EventID " +
                           "WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myExerciseData.add(new ExerciseEventData(rs.getInt(1), rs.getString(2),
                                   rs.getInt(3), rs.getString(4)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<ExerciseEventData, Integer>("myEventID"));
        myExerciseTypeCol.setCellValueFactory(new PropertyValueFactory<ExerciseEventData, String>("myExerciseType"));
        myIntensityLevelCol.setCellValueFactory(new PropertyValueFactory<ExerciseEventData, Integer>("myIntensityLevel"));
        myDistanceCol.setCellValueFactory(new PropertyValueFactory<ExerciseEventData, String>("myDistance"));
        myExerciseTable.setItems(null);
        myExerciseTable.setItems(myExerciseData);
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
