package Veterinarian;

import Database.dbConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class VeterinarianController implements Initializable {
    private int myUserID;
    private dbConnection myConnection;

    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
    }

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }
}
