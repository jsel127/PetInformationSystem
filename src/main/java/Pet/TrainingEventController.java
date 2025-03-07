package Pet;

import Database.dbConnection;
import Owner.OwnershipData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Training Event table.
 * @version March 5 2025
 * @author Jasmine Sellers, Selly Beyene
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
    @FXML
    private Label myErrorMessage;
    @FXML
    private TextField myExecutionTime;
    @FXML
    private TextField myCommandHeldTime;
    @FXML
    private TextArea myNotes;
    @FXML
    private Button myReturnEventPageBtn;
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
    @FXML
    public void loadTrainingEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myTrainingEventData = FXCollections.observableArrayList();

            String query = "SELECT Trainings.EventID, TrainingType, ExecutionTime, CommandHeldTime, Notes " +
                            "FROM Trainings JOIN TrainingTypes ON Trainings.TrainingTypeID = TrainingTypes.TrainingTypeID " +
                            "JOIN EventLogs ON Trainings.EventID = EventLogs.EventID " +
                            "WHERE petID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
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

    @FXML
    public void addTrainingEvent(ActionEvent theEvent) {
        String insertEventStatement = "INSERT INTO Trainings(EventID, TrainingTypeID, ExecutionTime, CommandHeldTime, Notes) VALUES(?, ?, ?, ?, ?);";
        try {
            if (myTrainingType.getValue() == null) {
                myErrorMessage.setText("A training type must be specified");
                return;
            }
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertEvent = conn.prepareStatement(insertEventStatement);
            prInsertEvent.setInt(1, myEventID);
            prInsertEvent.setInt(2, getMyTrainingTypeID());
            prInsertEvent.setObject(3, Integer.parseInt(myExecutionTime.getText()));
            prInsertEvent.setObject(4, Integer.parseInt(myCommandHeldTime.getText()));
            prInsertEvent.setObject(5, myNotes.getText());          //Source: https://stackoverflow.com/questions/30279125/insert-date-into-mysql-database-using-javafx-datepicker
            prInsertEvent.execute();
            conn.close();
            myErrorMessage.setText("Sucessfully added training event.");
        } catch (SQLException ex) {
            myErrorMessage.setText("Failed to create event. Only of each event type can be added for a given event.");
        }
    }

    /**
     * Gets the id of the grooming type.
     * @return key of the selected grooming type
     */
    @FXML
    private int getMyTrainingTypeID() {
        String queryGetTrainingTypeID = "SELECT TrainingTypeID FROM TrainingTypes WHERE TrainingType = ?;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prTrainingType = conn.prepareStatement(queryGetTrainingTypeID);
            prTrainingType.setString(1, myTrainingType.getValue().toString());
            ResultSet rsTrainingType = prTrainingType.executeQuery();
            if (rsTrainingType.next()) {
                return rsTrainingType.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1;
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
