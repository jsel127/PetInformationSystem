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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Medical Checkup Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class MedicalCheckupEventController implements Initializable{
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<MedicalCheckupEventData> myMedicalCheckupEventData;
    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private TableView<MedicalCheckupEventData> myMedicalCheckupTable;
    @FXML
    private TableColumn<MedicalCheckupEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, Integer> myVeterinarianIDCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, String> myVeterinarianCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, Integer> myVeterinaryIDCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, String> myVeterinaryCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, String> myWeightCol;
    @FXML
    private TableColumn<MedicalCheckupEventData, String> myNotesCol;
    @FXML
    private Label myVeterinaryQueryResult;
    @FXML
    private ComboBox mySelectedSpeciesAQ;
    @FXML
    private TextField myMinWeight;
    @FXML
    private TextField myMaxWeight;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
        initializeAnalyticalQuerySpeciesComboBox();
    }

    /**
     * Initializes options for species type combo box
     */
    private void initializeAnalyticalQuerySpeciesComboBox() {
        String query = "SELECT SpeciesName FROM Species";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> mealTypes = FXCollections.observableArrayList();
            while (rs.next()) {
                mealTypes.add(rs.getString(1));
            }
            mySelectedSpeciesAQ.setItems(mealTypes);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
     * Loads at most 50 events from the Medical Checkup Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadMedicalCheckupEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myMedicalCheckupEventData = FXCollections.observableArrayList();

            String query = "SELECT MedicalCheckups.EventID, Veterinarians.UserID, CONCAT(Users.FirstName, ' ', Users.LastName) AS VeterinarianFullName, " +
                    "Veterinary.VeterinaryID, Veterinary.VeterinaryName, MedicalCheckups.Weight, MedicalCheckups.Notes " +
                    "FROM MedicalCheckups JOIN EventLogs ON MedicalCheckups.EventID = EventLogs.EventID " +
                    "JOIN Veterinarians ON MedicalCheckups.VeterinarianID = Veterinarians.UserID " +
                    "JOIN Users ON Veterinarians.UserID = Users.UserID " +
                    "JOIN Veterinary ON MedicalCheckups.VeterinaryID = Veterinary.VeterinaryID " +
                    "WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myMedicalCheckupEventData.add(new MedicalCheckupEventData(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getString(6), rs.getString(7)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, Integer>("myEventID"));
        myVeterinarianIDCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, Integer>("myVeterinarianID"));
        myVeterinarianCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, String>("myVeterinarian"));
        myVeterinaryIDCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, Integer>("myVeterinaryID"));
        myVeterinaryCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, String>("myVeterinary"));
        myWeightCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, String>("myWeight"));
        myNotesCol.setCellValueFactory(new PropertyValueFactory<MedicalCheckupEventData, String>("myNotes"));
        myMedicalCheckupTable.setItems(null);
        myMedicalCheckupTable.setItems(myMedicalCheckupEventData);
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
     * Runs the first analytical query "Find the veterinary clinic that takes care of the most <species> (between __ and __kg).
     * Note: was adjusted to a query that can be specified by the user.
     * @param theEvent
     */
    @FXML
    public void runVeterinaryAnalyticalQuery(ActionEvent theEvent) {
        String analyticalQuery = "SELECT VeterinaryName " +
                "FROM Veterinary JOIN MedicalCheckups ON Veterinary.VeterinaryID = MedicalCheckups.VeterinaryID " +
                "JOIN EventLogs ON MedicalCheckups.EventID = EventLogs.EventID " +
                "JOIN Pets ON EventLogs.PetID = Pets.PetID " +
                "JOIN PetBreeds ON Pets.PetID = PetBreeds.PetID " +
                "JOIN Breeds ON PetBreeds.BreedID = Breeds.BreedID " +
                "JOIN Species ON Breeds.SpeciesID = Species.SpeciesID " +
                "WHERE SpeciesName = ? AND MedicalCheckups.Weight BETWEEN ? AND ? " +
                "GROUP BY VeterinaryName " +
                "ORDER BY Count(*) DESC " +
                "LIMIT 1;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(analyticalQuery);
            pr.setString(1, mySelectedSpeciesAQ.getValue().toString());
            pr.setBigDecimal(2, new BigDecimal(myMinWeight.getText()));
            pr.setBigDecimal(3, new BigDecimal(myMaxWeight.getText()));
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                myVeterinaryQueryResult.setText(rs.getString(1));
            } else {
                myVeterinaryQueryResult.setText("No such veterinary was found.");
            }
            conn.close();
        } catch (SQLException ex) {
            myVeterinaryQueryResult.setText("Please select a species and specify valid weight (postive and min is less than max)");
        } catch (NullPointerException ex) {
            myVeterinaryQueryResult.setText("Please select a species and specify valid weight (postive and min is less than max)");
        }
    }
}