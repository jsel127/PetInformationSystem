package Pet;

import Database.dbConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Training Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class TrainingEventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<TrainingEventData> myTrainingEventData;
    @FXML
    private LineChart<String, Integer> myLineChart;
    /***/
    @FXML
    private NumberAxis myYAxis;
    @FXML
    private CategoryAxis myXAxis;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        //myXAxis.setLabel("Time of Day");
        //myYAxis.setLabel("Command Held Time");
        //myLineChart.setTitle("Training Report");
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

}
