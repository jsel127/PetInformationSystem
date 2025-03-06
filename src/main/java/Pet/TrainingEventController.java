package Pet;

import Database.dbConnection;
import Owner.OwnershipData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
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
    private TableView<TrainingEventData> myTrainingEventTable;
    @FXML
    private TableColumn<TrainingEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<TrainingEventData, String> myTrainingTypeCol;
    @FXML
    private TableColumn<TrainingEventData, Integer> myExecutionTimeCol;
    @FXML
    private TableColumn<TrainingEventData, Integer> myCommandHeldTimeCol;
    @FXML
    private TableColumn<TrainingEventData, String> myNotesCol;
    @FXML
    private ComboBox myTrainingType;
    @FXML
    private LineChart<String, Integer> myLineChart;
    /***/
    @FXML
    private NumberAxis myYAxis;
    @FXML
    private CategoryAxis myXAxis;
    @FXML
    private Button myLogTrainingEventBtn;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        //myEventID = -1;
        myConnection = new dbConnection();
        initializeTrainingTypeComboBox();
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

    private void initializeTrainingTypeComboBox() {
        String query = "SELECT TrainingType FROM TrainingTypes";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> trainingTypes = FXCollections.observableArrayList();
            while (rs.next()) {
                trainingTypes.add(rs.getString(1));
            }
            myTrainingType.setItems(trainingTypes);
            //myPetOwnershipType.setItems(ownershipTypes);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

/*    @FXML
    public void generateErrorMessageIfTrainingTypeNull(MouseEvent theEvent) {
        if (myTrainingType.getValue() == null) {
            myPetMessage.setText("Pick a species first");
        }
    }*/
@FXML
public void loadTrainingEventData(ActionEvent theEvent) {
    try {
        Connection conn = dbConnection.getConnection();
        myTrainingEventData = FXCollections.observableArrayList();

        String query = "SELECT eventlogs.EventID, TrainingType, ExecutionTime, CommandHeldTIme, Notes\n" +
                "FROM trainingtypes\n" +
                "JOIN trainings ON trainings.TrainingTypeID = trainingtypes.TrainingTypeID\n" +
                "JOIN eventlogs ON trainings.EventID = eventlogs.EventID\n" +
                "JOIN pets ON eventlogs.PetID = pets.PetID\n" +
                "WHERE petID = " + myPetID + "\n" +
                "LIMIT 10;";
        PreparedStatement pr = conn.prepareStatement(query);
        pr.setInt(1, myEventID);
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {
            myTrainingEventData.add(new TrainingEventData(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getInt(4), rs.getString(5)));
        }
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    myEventIDCol.setCellValueFactory(new PropertyValueFactory<TrainingEventData, Integer>("myEventID"));
    myTrainingTypeCol.setCellValueFactory(new PropertyValueFactory<TrainingEventData, String>("myTrainingType"));
    myExecutionTimeCol.setCellValueFactory(new PropertyValueFactory<TrainingEventData, Integer>("myExecutionTime"));
    myCommandHeldTimeCol.setCellValueFactory(new PropertyValueFactory<TrainingEventData, Integer>("myCommandHeldTime"));
    myNotesCol.setCellValueFactory(new PropertyValueFactory<TrainingEventData, String>("myNotes"));

    myTrainingEventTable.setItems(null);
    myTrainingEventTable.setItems(myTrainingEventData);
}

/*    @FXML
    public void addTrainingEvent(ActionEvent theEvent) {
        String insertEventStatement = "INSERT INTO Trainings(EventID, TrainingTypeID, ExecutionTime, CommandHeldTime, Notes) VALUES(?, ?, ?);";
        try {
            if (myTrainingType.getValue() == null) {
                //myErrorMessage.setText("A start date and time must be specified.");
                return;
            }
            if (myExecutionTimeCol.getText() == "") {

            }
            if (myCommandHeldTimeCol.getText() == "") {
                //myErrorMessage.setText("A end date and time must be specified.");
                return;
            }
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertEvent = conn.prepareStatement(insertEventStatement, Statement.RETURN_GENERATED_KEYS);
            prInsertEvent.setInt(1, myEventID);
            prInsertEvent.setInt(2, getMyTrainingType(myOwnershipType.getValue().toString()));
            prInsertEvent.setInt(3, );
            prInsertEvent.setDate(4, Date.valueOf(myDateOfAdoption.getValue()));          //Source: https://stackoverflow.com/questions/30279125/insert-date-into-mysql-database-using-javafx-datepicker
            prInsertEvent.execute();
            conn.close();
            ResultSet keyData = prInsertEvent.getGeneratedKeys();
            if (keyData.next()) {
                myEventID = keyData.getInt(1);
                myEventIDSelectedLabel.setText(Integer.toString(myEventID));
                updateButtonStatuses(ENABLED);
            }
            myErrorMessage.setText("");
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            myErrorMessage.setText("Failed to create event. Check formatting of date.");
        }
    }*/

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
