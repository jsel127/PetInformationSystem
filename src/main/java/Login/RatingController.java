package Login;
/**
 * Controller class to connect UI to database for the Potty Event table.
 * @version March 5 2025
 * @author Selly Beyene
 */
import Database.dbConnection;
import Pet.ExerciseEventReportData;
import Pet.PottyEventData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RatingController implements Initializable {
    /** The PetID for the event */
    private int myUserID;
    /** the EventID for the event */
    private int myCompanyID;
    /** The connection to the database */
    private dbConnection myConnection;
    /** Stores loaded events */
    private ObservableList<PottyEventData> myPottyData;

    @FXML
    private ComboBox myCompanyType;
    @FXML
    private ComboBox myRatingValue;
    @FXML
    private TextArea myComment;
    @FXML
    private Label myErrorMessage;
    @FXML
    private Button myViewRatingsBtn;
    @FXML
    private Button myReturnEventPageBtn;
    @FXML
    private TableView<RatingData> myRatingsTable;
    @FXML
    private TableColumn<RatingData, String> myRatingValueCol;
    @FXML
    private TableColumn<RatingData, String> myNotesCol;
    /** Stores loaded events */
    private ObservableList<ExerciseEventReportData> myRatingData;
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myConnection = new dbConnection();
        //initializePottyTypeComboBox();
    }
    /**
     * Sets the UserID for the event
     * @param theUserID the key for the Pets table.
     */
    public void setUserID(int theUserID) {
        if (theUserID < 0) {
            throw new IndexOutOfBoundsException();
        }
        myUserID = theUserID;
    }
/*    @FXML
    public void addRating(ActionEvent theEvent) {
        int userID = Integer.parseInt(myComment.getText());
        if (myCompanyType != null && myRatingValue != null) {
            switch (((ratingType)myCompanyType.getValue()).toString()) {
                case "Groomer":
                    addGroomerRating();
                    break;
                case "Veterinarian":
                    veterinaryLogin(userID);
                    break;
                case "Veterinary":
                    caretakerLogin(userID);
                    break;
                case "CareTaker":
                    caretakerLogin(userID);
                    break;
                case "Veterinary":
                    caretakerLogin(userID);
                    break;
        try {

                }
                Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insertNewUser, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, myPassword.getText());
                stmt.setString(2, myUserFirstName.getText());
                stmt.setString(3, myUserLastName.getText());
                stmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
                stmt.setInt(5, vetValue);
                stmt.setInt(6, careValue);
                stmt.setInt(7, 0);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userID = generatedKeys.getInt(1);
                        myLoginMessage.setText("You have successfully created an account! You're UserID is " + userID);
                    }
                    //backToLogin();
                } else {
                    myLoginMessage.setText("Signup failed. Please try again.");
                }
            } else {
                myLoginMessage.setText("Incorrect credentials. Try again.");
            }
        } catch (Exception localException) {

        }
    }*/

    @FXML
    public void addGroomerRating() {

    }
    @FXML
    public void addVeterinarianRating() {

    }
    @FXML
    public void addVeterinaryRating() {

    }
    @FXML
    public void addCaretakerRating() {

    }
    @FXML
    public void addDaycareRating() {

    }
}
