package Caretaker;

import Database.dbConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class CaretakerController {
    private int myUserID;
    private dbConnection myConnection;

    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
    }

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }
}
