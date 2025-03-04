package Owner;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

public class OwnerController implements Initializable {
    private int myUserID;
    @FXML
    private TextField myPetID;
    @FXML
    private ComboBox myOwnershipType;
    @FXML
    private Button myAddOwnershipFormBtn;
    @FXML
    private Button myClearOwnershipFormBtn;

    @FXML
    private TextField myPetFirstName;
    @FXML
    private TextField myPetLastName;
    @FXML
    private DatePicker myPetDOB;
    @FXML
    private ComboBox myPetGender;
    @FXML
    private ComboBox myPetInsurance;
    @FXML
    private ComboBox myPetOwnershipType;
    @FXML
    private ComboBox myPetSpecies;
    @FXML
    private ComboBox myPetBreed;

    @FXML
    private Button myLoadDataBtn;

    @FXML
    private TableView<OwnershipData> myOwnershipTable;
    @FXML
    private TableColumn<OwnershipData, String> myPetIDCol;
    @FXML
    private TableColumn<OwnershipData, String> myPetCol;
    @FXML
    private TableColumn<OwnershipData, String> myOwnershipTypeCol;
    @FXML
    private TableColumn<OwnershipData, String> myDateOfAdoptionCol;

    private dbConnection myConnection;
    private ObservableList<OwnershipData> myOwnershipData;

    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
    }

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }

    @FXML
    public void loadOwnershipData(ActionEvent event) {
        try {
            Connection conn = dbConnection.getConnection();
            myOwnershipData = FXCollections.observableArrayList();

            String query = "SELECT Pets.PetID, Pets.FirstName, OwnershipTypes.OwnershipType, Ownerships.`Date` " +
                           "FROM Ownerships JOIN Pets ON Ownerships.PetID = Pets.PetID " +
                           "JOIN OwnershipTypes ON Ownerships.OwnershipTypeID = OwnershipTypes.OwnershipTypeID " +
                           "WHERE Ownerships.UserID = ?;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myUserID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myOwnershipData.add(new OwnershipData(rs.getString(1), rs.getString(2),
                                                      rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myPetIDCol.setCellValueFactory(new PropertyValueFactory<OwnershipData, String>("myPetID"));
        myPetCol.setCellValueFactory(new PropertyValueFactory<OwnershipData, String>("myPet"));
        myOwnershipTypeCol.setCellValueFactory(new PropertyValueFactory<OwnershipData, String>("myOwnershipType"));
        myDateOfAdoptionCol.setCellValueFactory(new PropertyValueFactory<OwnershipData, String>("myDateOfAdoption"));

        myOwnershipTable.setItems(null);
        myOwnershipTable.setItems(myOwnershipData);
    }
}
