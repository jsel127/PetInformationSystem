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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class to connect UI to database for the Grooming Event table.
 * @version March 5 2025
 * @author Jasmine Sellers
 */
public class GroomingEventController implements Initializable {
    /** The PetID for the event */
    private int myPetID;
    /** the EventID for the event */
    private int myEventID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<GroomingEventData> myGroomingEventData;
    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private TableView<GroomingEventData> myGroomingTable;
    @FXML
    private TableColumn<GroomingEventData, Integer> myEventIDCol;
    @FXML
    private TableColumn<GroomingEventData, String> myGroomingTypeCol;
    @FXML
    private TableColumn<GroomingEventData, Integer> myGroomerIDCol;
    @FXML
    private ComboBox myCityAQ;
    @FXML
    private TextField myMinNumberRatings;
    @FXML
    private Label myGroomerQueryResult;
    /** Initializes the window and connects to the database */
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
        initializeCitiesComboBox();
    }

    private void initializeCitiesComboBox() {
        String query = "SELECT CityName FROM Cities";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> cityNames = FXCollections.observableArrayList();
            while (rs.next()) {
                cityNames.add(rs.getString(1));
            }
            myCityAQ.setItems(cityNames);
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
     * Loads at most 50 events from the Grooming Event Table and stores it to be loaded in the UI.
     * @param theEvent the action taken.
     */
    @FXML
    public void loadGroomingEventData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myGroomingEventData = FXCollections.observableArrayList();

            String query = "SELECT Groomings.EventID, GroomingTypes.GroomingType, GroomerID " +
                           "FROM Groomings JOIN GroomingTypes ON Groomings.GroomingTypeID = GroomingTypes.GroomingTypeID " +
                           "JOIN EventLogs ON Groomings.EventID = EventLogs.EventID " +
                           "WHERE PetID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myPetID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myGroomingEventData.add(new GroomingEventData(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myEventIDCol.setCellValueFactory(new PropertyValueFactory<GroomingEventData, Integer>("myEventID"));
        myGroomingTypeCol.setCellValueFactory(new PropertyValueFactory<GroomingEventData, String>("myGroomingType"));
        myGroomerIDCol.setCellValueFactory(new PropertyValueFactory<GroomingEventData, Integer>("myGroomerID"));
        myGroomingTable.setItems(null);
        myGroomingTable.setItems(myGroomingEventData);
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
     * Runs the fourth analytical query "For a given city what is the groomer that has the highest average rating and has over 10 ratings."
     * Note: was adjusted to a query that can be specified by the user.
     * @param theEvent the triggering event
     */
    @FXML
    public void runGroomerAnalyticalQuery(ActionEvent theEvent) {
        String analyticalQuery = "SELECT `Name`, Groomers.Contact, AVG(GroomerRatings.RatingValue) AS AverageRating, COUNT(*) AS NumberOfRatings " +
                                "FROM Groomers JOIN Addresses ON Groomers.AddressID = Addresses.AddressID " +
                                "JOIN PostalCodes ON Addresses.PostalID = PostalCodes.PostalID " +
                                "JOIN Cities ON PostalCodes.CityID = Cities.CityID " +
                                "JOIN GroomerRatings ON Groomers.GroomerID = GroomerRatings.GroomerID " +
                                "WHERE Cities.CityName = ? " +
                                "GROUP BY Groomers.GroomerID, Name " +
                                "HAVING COUNT(GroomerRatings.RatingValue) > ? " +
                                "ORDER BY AVG(GroomerRatings.RatingValue) " +
                                "LIMIT 1;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(analyticalQuery);
            pr.setString(1, myCityAQ.getValue().toString());
            pr.setInt(2, Integer.parseInt(myMinNumberRatings.getText()));
            ResultSet rs = pr.executeQuery();
            if (rs.next() && rs.getString(1) != null) {
                String formattedResult = String.format("Name: %s \nContact: %s \nAverage Rating: %s \nNumber Ratings: %d",
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                myGroomerQueryResult.setText(formattedResult);
            } else {
                myGroomerQueryResult.setText("No groomers were found in the given area");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            myGroomerQueryResult.setText("Invalid input received.");
        } catch (NullPointerException ex) {
            myGroomerQueryResult.setText("Sections above must be specified.");
        }
    }
}
