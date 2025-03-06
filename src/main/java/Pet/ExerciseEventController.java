package Pet;

import Database.dbConnection;
import Owner.ExpenseData;
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
    private ObservableList<ExerciseEventData> myExerciseEventData;
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
