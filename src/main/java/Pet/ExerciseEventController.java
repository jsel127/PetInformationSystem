package Pet;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    /***/
    @FXML
    private NumberAxis myYAxis;
    @FXML
    private CategoryAxis myXAxis;
    private ObservableList<ExerciseEventData> myExerciseData;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
    }
    /** Stores loaded events */
    private ObservableList<ExerciseEventReportData> myExerciseEventData;
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
        myPetID = theEventID;
    }
    /**
     * Loads at most 50 events from the Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadExerciseEventDate(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myExerciseData = FXCollections.observableArrayList();

            String query = "SELECT Exercises.EventID, ExerciseTypes.ExerciseName, IntensityLevel, Distance " +
                           "FROM Exercises JOIN ExerciseTypes ON Exercises.ExerciseTypeID = ExerciseTypes.ExerciseTypeID " +
                           "JOIN EventLogs ON Exercises.EventID = EventLogs.EventID " +
                           "WHERE PetID = ? LIMIT 50";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myExerciseData.add(new ExerciseEventData(rs.getInt(1), rs.getString(2),
                                   rs.getInt(3), rs.getString(3)));
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

    public void generateBarChart() {
        try {
            Connection conn = dbConnection.getConnection();

            String query = "SELECT ExerciseName, COUNT(*) AS TotalRepetitionsOfExercise, AVG(IntensityLevel) AS AverageIntensity\n" +
                    "FROM Exercises JOIN EventLogs ON Exercises.EventID = EventLogs.EventID\n" +
                    "\t\t\t   JOIN Pets ON EventLogs.PetID = Pets.PetID\n" +
                    "               JOIN ExerciseTypes on Exercises.ExerciseTypeID = ExerciseTypes.ExerciseTypeID\n" +
                    "WHERE Pets.PetID = 1\n" +
                    "GROUP BY ExerciseName\n" +
                    "ORDER BY COUNT(*);";
            PreparedStatement pr = conn.prepareStatement(query);
            //pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();
            XYChart.Series<String, Integer> exerciseChart = new XYChart.Series<>();
            exerciseChart.setName("ExerciseReport");

            while (rs.next()) {
                exerciseChart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            myBarChart.getData().clear();
            myBarChart.getData().add(exerciseChart);
            //conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
