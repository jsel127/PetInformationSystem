package Pet;

import Database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    private int myPetID;
    private dbConnection myConnection;
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();

    }
    public void setPetID(int thePetID) {
        myPetID = thePetID;
    }
}
