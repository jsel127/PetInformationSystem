package Pet;

import Database.dbConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    private int myPetID;
    private dbConnection myConnection;
    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();

    }

    public void setPetID(int thePetID) {
        myPetID = thePetID;
    }
}
