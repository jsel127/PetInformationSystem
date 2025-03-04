package PetInformationSystem.login;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private LoginModel loginModel = new LoginModel();
    @FXML
    private Label myDBConnectionLabel;
    @FXML
    private TextField myUsername;
    @FXML
    private TextField myPassword;
    @FXML
    private Button myLoginButton;
    @FXML
    private ComboBox myUserType;
    public void initialize(URL theURL, ResourceBundle theRB) {
        if (loginModel.isDatabaseConnected()) {
            myDBConnectionLabel.setText("Connected to Database");
        } else {
            myDBConnectionLabel.setText("Not Connected to Database");
        }
        myUserType.setItems(FXCollections.observableArrayList(userType.values()));
    }

    @FXML
    public void login(ActionEvent theEvent) {

    }

    public void ownerLogin(ActionEvent theEvent) {

    }

    public void veterinaryLogin(ActionEvent theEvent) {

    }

    public void caretakerLogin(ActionEvent theEvent) {

    }
}