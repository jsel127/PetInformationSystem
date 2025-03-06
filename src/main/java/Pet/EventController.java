package Pet;

import Database.dbConnection;
import Owner.OwnershipData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
/**
 * Controller class to connect UI to database for the Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class EventController implements Initializable {
    private final static boolean ENABLED = false;
    /** The PetID for the event */
    private int myPetID;
    /** The selected EventID  */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<EventData> myEventData;

    @FXML
    private DatePicker myStartDate;
    @FXML
    private TextField myStartTime;
    @FXML
    private DatePicker myEndDate;
    @FXML
    private TextField myEndTime;
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
    private Label myErrorMessage;
    @FXML
    private Label myEventIDSelectedLabel;
    @FXML
    private Label myAddEventErrorMessage;
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
        myEventID = -1;
        myConnection = new dbConnection();
        updateButtonStatuses(!ENABLED);
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
     * Adds an event based
     * @param theEvent the triggering event
     */
    @FXML
    public void addEvent(ActionEvent theEvent) {
        String insertEventStatement = "INSERT INTO EventLogs(PetID, StartDateTime, EndDateTime) VALUES(?, ?, ?);";
        try {
            if (myStartDate.getValue() == null || myStartTime.getText() == "") {
                myErrorMessage.setText("A start date and time must be specified.");
                return;
            }
            if (myEndDate.getValue() ==  null || myEndTime.getText() == "") {
                myErrorMessage.setText("A end date and time must be specified.");
                return;
            }
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertEvent = conn.prepareStatement(insertEventStatement, Statement.RETURN_GENERATED_KEYS);
            prInsertEvent.setInt(1, myPetID);
            String startDateTimeFormatted = Date.valueOf(myStartDate.getValue()).toString() + " " + myStartTime.getText().strip();
            String endDateTimeFormatted = Date.valueOf(myEndDate.getValue()).toString() + " " + myEndTime.getText().strip();
            prInsertEvent.setString(2, startDateTimeFormatted);
            prInsertEvent.setString(3, endDateTimeFormatted);
            prInsertEvent.execute();
            ResultSet keyData = prInsertEvent.getGeneratedKeys();
            if (keyData.next()) {
                myEventID = keyData.getInt(1);
                myEventIDSelectedLabel.setText(Integer.toString(myEventID));
                updateButtonStatuses(ENABLED);
            }
            conn.close();
            myErrorMessage.setText("Successfully added event ID:" + myEventID);
        } catch (SQLException ex) {
            ex.printStackTrace();
            myErrorMessage.setText("Failed to create event. Check formatting of date. Start date must be before end date.");
        }
    }

    /**
     * Loads the potty event page to log potty events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadPottyEventPage(ActionEvent theEvent) {
        if (myPottyEventBtn.isDisabled()){
            myAddEventErrorMessage.setText("Please select a event to add event types to.");
            return;
        }
        try {
            Stage currentStage = (Stage) myPottyEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/pottyEvent.fxml").openStream());

            PottyEventController eventController = (PottyEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Potty Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads the meal event page to log meal events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadMealEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myMealEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/mealEvent.fxml").openStream());

            MealEventController eventController = (MealEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Meal Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the exercise event page to log exercise events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadExerciseEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myExerciseEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/exerciseEvent.fxml").openStream());

            ExerciseEventController eventController = (ExerciseEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Exercise Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the training event page to log training events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadTrainingEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myTrainingEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/trainingEvent.fxml").openStream());

            TrainingEventController eventController = (TrainingEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Training Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the grooming event page to log grooming events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadGroomingEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myGroomingEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/groomingEvent.fxml").openStream());

            GroomingEventController eventController = (GroomingEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Grooming Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the medical checkup event page to log medical checkup events.
     * @param theEvent the triggering event
     */
    @FXML
    public void loadMedicalCheckupEventPage(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myMedicalCheckupEventBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/medicalCheckupEvent.fxml").openStream());

            MedicalCheckupEventController eventController = (MedicalCheckupEventController) loader.getController();
            eventController.setPetID(myPetID);
            eventController.setEventID(myEventID);

            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Medical Checkup Event Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Changes the event ID currently selected. This is used to set the event that will be changed when
     * adding an event types (e.g. Exercise Event).
     * @param theEvent the triggering event.
     */
    @FXML
    public void changeEventSelected(MouseEvent theEvent) {
        EventData selectedPet = myEventTable.getSelectionModel().getSelectedItem();
        if (selectedPet != null) {
            myEventID = selectedPet.getMyEventID();
            myEventIDSelectedLabel.setText(Integer.toString(selectedPet.getMyEventID()));
            updateButtonStatuses(ENABLED);
        }
    }

    /**
     * Returns to the Pet Dashboard.
     * @param theEvent the event that triggered the return.
     */
    @FXML
    public void returnToPetDashboard(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myReturnPetPageBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/pet.fxml").openStream());

            PetController ownerController = (PetController) loader.getController();
            ownerController.setPetID(myPetID);
            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Pet Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disable/Enables the buttons to add an event type
     * @param theStatus true if the buttons should be disabled, false otherwise.
     */
    private void updateButtonStatuses(boolean theStatus) {
        ArrayList<Button> buttonsToChangeStatus = new ArrayList<>(Arrays.asList(myPottyEventBtn, myMealEventBtn, myExerciseEventBtn, myTrainingEventBtn, myGroomingEventBtn, myMedicalCheckupEventBtn));
        for (Button buttonToChangeStatus : buttonsToChangeStatus) {
            buttonToChangeStatus.setDisable(theStatus);
        }
    }
}
