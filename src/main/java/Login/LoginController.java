package Login;

import Owner.OwnerController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private LoginModel loginModel = new LoginModel();
    @FXML
    private Label myDBConnectionLabel;
    @FXML
    private TextField myUserID;
    @FXML
    private TextField myPassword;
    @FXML
    private Button myLoginButton;
    @FXML
    private ComboBox myUserType;
    @FXML
    private Label myLoginMessage;
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
        try {
            if (loginModel.isLogin(Integer.parseInt(myUserID.getText()), myPassword.getText(), ((userType) myUserType.getValue()).toString())) {
                Stage stage = (Stage) this.myLoginButton.getScene().getWindow();
                stage.close();
                switch (((userType)myUserType.getValue()).toString()) {
                    case "Owner":
                        ownerLogin();
                        break;
                    case "Veterinarian":
                        veterinaryLogin();
                        break;
                    case "Caretaker":
                        caretakerLogin();
                        break;
                }
            } else {
                myLoginMessage.setText("Incorrect credentials. Try again.");
            }
        } catch (Exception localException) {

        }
    }

    public void ownerLogin() {
        try {
            Stage ownerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Owner/owner.fxml"));

            OwnerController ownerController = (OwnerController) loader.getController();

            Scene scene = new Scene(root);
            ownerStage.setScene(scene);
            ownerStage.setTitle("Owner Dashboard");
            ownerStage.setResizable(false);
            ownerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void veterinaryLogin() {
        try {
            Stage veterinaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Veterinarian/veterinarian.fxml"));

            Scene scene = new Scene(root);
            veterinaryStage.setScene(scene);
            veterinaryStage.setTitle("Veterinary Dashboard");
            veterinaryStage.setResizable(false);
            veterinaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void caretakerLogin() {
        try {
            Stage caretakerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Caretaker/caretaker.fxml"));

            Scene scene = new Scene(root);
            caretakerStage.setScene(scene);
            caretakerStage.setTitle("Caretaker Dashboard");
            caretakerStage.setResizable(false);
            caretakerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}