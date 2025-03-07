package Pet;

import Database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    private int myPetID;
    @FXML
    private ComboBox myTrainingType;
    @FXML
    private LineChart<String, Integer> myLineChart;
    /** Bar chart to track the event types */
    @FXML
    private BarChart<String, Integer> myBarChart;

    private dbConnection myConnection;
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();

    }
    public void setPetID(int thePetID) {
        myPetID = thePetID;
    }

    public void generateLineChart() {
        try {
            Connection conn = dbConnection.getConnection();

            String query = "SELECT firstname, trainingtype, commandheldtime, TIME(StartDateTime) AS TimeOfDay\n" +
                    "FROM pets JOIN eventlogs ON pets.PetID = eventlogs.PetID\n" +
                    "\t\t  JOIN trainings ON eventlogs.EventID = trainings.EventID\n" +
                    "\t\t  JOIN trainingtypes ON trainings.TrainingTypeID = TrainingTypes.TrainingTypeID\n" +
                    "ORDER BY commandheldtime DESC;";
            PreparedStatement pr = conn.prepareStatement(query);
            //pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();
            XYChart.Series trainingChart = new XYChart.Series<>();
            trainingChart.setName("TrainingReport");

            while (rs.next()) {
                trainingChart.getData().add(new XYChart.Data<>(rs.getString(4), rs.getInt(3)));
            }

            myLineChart.getData().clear();
            myLineChart.getData().addAll(trainingChart);
            //conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
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
            exerciseChart.setName("Report");

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
