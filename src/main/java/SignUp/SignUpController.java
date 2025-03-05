package SignUp;

import Database.dbConnection;
import Login.LoginApp;
import Login.LoginController;
import Login.userType;
import Owner.OwnerController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private SignUpModel signUpModel = new SignUpModel();

    @FXML
    private Label myDBConnectionLabel;
    @FXML
    private TextField myUserFirstName;
    @FXML
    private TextField myUserLastName;
    @FXML
    private TextField myPhoneNumber;
    @FXML
    private TextField myEmail;
    @FXML
    private TextField myPassword;
    @FXML
    private Button myCreateAccountButton;
    @FXML
    private RadioButton isOwner;
    @FXML
    private RadioButton isVeterinarian;
    @FXML
    private RadioButton isCaretaker;
    @FXML
    private TextField myLoginMessage;
    private dbConnection myConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (signUpModel.isDatabaseConnected()) {
            myDBConnectionLabel.setText("Connected to Database");
        } else {
            myDBConnectionLabel.setText("Not Connected to Database");
        }
        //myUserType.setItems(FXCollections.observableArrayList(userType.values()));
    }

    @FXML
    public void signup() {
        int vetValue;
        int careValue;
        if (isVeterinarian.isSelected()) {
            vetValue = 1;
        } else {
            vetValue = 0;
        }
        if (isCaretaker.isSelected()) {
            careValue = 1;
        } else {
            careValue = 0;
        }
        String insertNewUser = "INSERT INTO Users(Password, FirstName, LastName, DateJoined, IsVeterinarian, " +
                "IsCaretaker, IsLoggedin) VALUES(?, ?, ?, ?, ?, ?, ?);";
        try {
            /*if (myUserFirstName.getText() == null) {
                myLoginMessage.setText("Must enter a first name.");
                return;
            }*/
            // add more null cases later.
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertNewUser);
            stmt.setString(1, myPassword.getText());
            stmt.setString(2, myUserFirstName.getText());
            stmt.setString(3, myUserLastName.getText());
            stmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.setInt(5, vetValue);
            stmt.setInt(6, careValue);
            stmt.setInt(7, 0);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                backToLogin();
            } else {
                myLoginMessage.setText("Signup failed. Please try again.");
            }
        } catch (Exception localException) {
            //myLoginMessage.setText("Database error: " + localException.getMessage());
            localException.printStackTrace();
        }
    }

    public void backToLogin() {
        try {
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Login/login.fxml").openStream());

            LoginController loginController = (LoginController) loader.getController();

            Scene scene = new Scene(root);
            loginStage.setScene(scene);
            loginStage.setTitle("Login");
            loginStage.setResizable(false);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
