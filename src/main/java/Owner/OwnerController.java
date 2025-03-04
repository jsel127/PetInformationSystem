package Owner;

import Database.dbConnection;
import Login.userType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
public class OwnerController implements Initializable {
    private int myUserID;
    @FXML
    private TextField myPetID;
    @FXML
    private ComboBox myOwnershipType;
    @FXML
    private DatePicker myDateOfAdoption;
    @FXML
    private Label myOwnershipMessage;

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
    private Label myPetMessage;

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
        initializePetOwnershipTypeComboBox();
        initializeGenderComboBox();
        initializeInsuranceComboBox();
        initializeSpeciesComboBox();
    }

    private void initializePetOwnershipTypeComboBox() {
        String query = "SELECT OwnershipType FROM OwnershipTypes";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> ownershipTypes = FXCollections.observableArrayList();
            while (rs.next()) {
                ownershipTypes.add(rs.getString(1));
            }
            myOwnershipType.setItems(ownershipTypes);
            myPetOwnershipType.setItems(ownershipTypes);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeGenderComboBox() {
        myPetGender.setItems(FXCollections.observableArrayList("Male", "Female", "Unknown"));
    }

    private void initializeInsuranceComboBox() {
        String query = "SELECT Company, Plan FROM Insurances";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> insurances = FXCollections.observableArrayList();
            while (rs.next()) {
                insurances.add(rs.getString(1) + ", " + rs.getString(2));
            }
            myPetInsurance.setItems(insurances);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeSpeciesComboBox() {
        String query = "SELECT SpeciesName FROM Species";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> species = FXCollections.observableArrayList();
            while (rs.next()) {
                species.add(rs.getString(1));
            }
            myPetSpecies.setItems(species);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void initializeBreedComboBox(ActionEvent theEvent) {
        String query = "SELECT BreedName FROM Breeds WHERE Breeds.SpeciesID = (SELECT SpeciesID FROM Species WHERE SpeciesName = ?);";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setString(1, myPetSpecies.getValue().toString());
            ResultSet rs = pr.executeQuery();
            ObservableList<String> breeds = FXCollections.observableArrayList();
            while (rs.next()) {
                breeds.add(rs.getString(1));
            }
            myPetBreed.setItems(breeds);
            conn.close();

            myPetMessage.setText(null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void generateErrorMessageIfSpeciesNull(MouseEvent theEvent) {
        if (myPetSpecies.getValue() == null) {
            myPetMessage.setText("Pick a species first");
        }
    }

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }

    @FXML
    public void loadOwnershipData(ActionEvent theEvent) {
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

    @FXML
    public void addOwnership(ActionEvent theEvent) {
        String getOwnershipTypeId = "SELECT OwnershipTypeID FROM OwnershipTypes WHERE OwnershipType = ?;";
        String insertStatement = "INSERT INTO Ownerships(UserID, PetID, OwnershipTypeID, Date) VALUES(?, ?, ?, ?);";
        try {
            if (myPetID.getText() == null) {
                myOwnershipMessage.setText("An petID must be selected.");
                return;
            }
            if (myOwnershipType.getValue() == null) {
                myOwnershipMessage.setText("An ownership type must be selected.");
                return;
            }
            Connection conn = dbConnection.getConnection();
            PreparedStatement prOwnershipType = conn.prepareStatement(getOwnershipTypeId);
            prOwnershipType.setString(1, myOwnershipType.getValue().toString());
            ResultSet rsOwnershipType = prOwnershipType.executeQuery();

            if (rsOwnershipType.next()) {
                PreparedStatement prInsert = conn.prepareStatement(insertStatement);
                prInsert.setInt(1, myUserID);
                prInsert.setInt(2, Integer.parseInt(myPetID.getText()));
                prInsert.setInt(3, rsOwnershipType.getInt(1));
                prInsert.setDate(4, Date.valueOf(myDateOfAdoption.getValue()));          //Source: https://stackoverflow.com/questions/30279125/insert-date-into-mysql-database-using-javafx-datepicker
                prInsert.execute();
            }
            conn.close();
        } catch (SQLException ex) {
            myOwnershipMessage.setText("Ownership could not be added. A relationship already exists.");
        }
    }

    @FXML
    public void clearOwnershipForm(ActionEvent theEvent) {
        myPetID.clear();
        myDateOfAdoption.setValue(null);
        myOwnershipType.setValue(null);
    }

    public void addPet(ActionEvent theEvent) {

    }

    @FXML
    public void clearPetForm(ActionEvent theEvent) {
        myPetFirstName.clear();
        myPetLastName.clear();
        myPetDOB.setValue(null);
        myPetGender.setValue(null);
        myPetInsurance.setValue(null);
        myPetOwnershipType.setValue(null);
        myPetSpecies.setValue(null);
        myPetBreed.setValue(null);
    }
}
