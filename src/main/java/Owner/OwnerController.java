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
    private DatePicker myPetDOA;
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
            conn.close();
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
            PreparedStatement prInsert = conn.prepareStatement(insertStatement);
            prInsert.setInt(1, myUserID);
            prInsert.setInt(2, Integer.parseInt(myPetID.getText()));
            prInsert.setInt(3, getOwnershipTypeID(myOwnershipType.getValue().toString()));
            prInsert.setDate(4, Date.valueOf(myDateOfAdoption.getValue()));          //Source: https://stackoverflow.com/questions/30279125/insert-date-into-mysql-database-using-javafx-datepicker
            prInsert.execute();
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

    @FXML
    public void addPetAndOwnership(ActionEvent theEvent) {
        if (myPetFirstName.getText() == null) {
            myPetMessage.setText("Pet's first name was not specified.");
            return;
        }
        if (myPetBreed.getValue() == null) {
            myPetMessage.setText("Pet's breed was not specified");
            return;
        }
        if (myPetOwnershipType.getValue() == null) {
            myPetMessage.setText("Pet's ownership type to user was not specified");
            return;
        }
        addPet();
        addOwnershipFromPetFrom();
    }

    private void addPet() {
        String insertPetStatement = "INSERT INTO Pets (InsuranceID, FirstName, LastName, DOB, Gender) " +
                                    "VALUES (?, ?, ?, ?, ?);";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsertPet = conn.prepareStatement(insertPetStatement);
            int insuranceID = getInsuranceID();
            prInsertPet.setObject(1, (insuranceID >= 0) ? insuranceID : null, Types.INTEGER); // Source: https://blogs.oracle.com/javamagazine/post/quiz-yourself-working-with-preparedstatement-and-sql-null-values-in-java, https://learn.microsoft.com/en-us/sql/connect/jdbc/reference/setobject-method-int-java-lang-object-int?view=sql-server-ver16             prInsertPet.setString(2, java.lang.Object myPetFirstName.getText());
            prInsertPet.setString(2, myPetFirstName.getText());
            prInsertPet.setObject(3, myPetLastName.getText());
            Date dob = (myPetDOB.getValue() != null) ? Date.valueOf(myPetDOB.getValue()) : null;
            prInsertPet.setObject(4, dob);
            Object gender = myPetGender.getValue();
            prInsertPet.setObject(5, (gender != null) ? gender.toString() : null);
            prInsertPet.execute();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            myPetMessage.setText("Failed to create pet. ");
        }
    }

    private void addOwnershipFromPetFrom() {
        String insertOwnershipStatement = "INSERT INTO Ownerships(UserID, PetID, OwnershipTypeID, Date) VALUES(?, ?, ?, ?);";
        try {
            Connection conn = dbConnection.getConnection();
            // Source: https://www.w3schools.com/sql/func_mysql_last_insert_id.asp
            PreparedStatement getLastIndex = conn.prepareStatement("SELECT MAX(PetID) FROM Pets;");
            ResultSet rs = getLastIndex.executeQuery();
            if (rs.next()) {
                int PetID = rs.getInt(1);
                System.out.println("PetID" + PetID);
                PreparedStatement prInsertPet = conn.prepareStatement(insertOwnershipStatement);
                prInsertPet.setInt(1, myUserID);
                prInsertPet.setInt(2, PetID);
                prInsertPet.setInt(3, getOwnershipTypeID(myPetOwnershipType.getValue().toString()));
                Date doa = (myPetDOA.getValue() != null) ? Date.valueOf(myPetDOA.getValue()) : null;
                prInsertPet.setObject(4, doa);
                System.out.println(prInsertPet.toString());
                prInsertPet.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            myPetMessage.setText("Failed to create relationship to pet.");
        }
    }

    private int getOwnershipTypeID(String theOwnershipType) {
        String getOwnershipTypeId = "SELECT OwnershipTypeID FROM OwnershipTypes WHERE OwnershipType = ?;";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prOwnershipType = conn.prepareStatement(getOwnershipTypeId);
            prOwnershipType.setString(1, theOwnershipType);
            ResultSet rsOwnershipType = prOwnershipType.executeQuery();
            if (rsOwnershipType.next()) {
                return rsOwnershipType.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    private int getInsuranceID() {
        String query = "SELECT InsuranceID FROM Insurances WHERE Company = ? AND Plan = ?";
        if (myPetInsurance.getValue() != null) {
            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement pr = conn.prepareStatement(query);
                String[] insuranceInfo = myPetInsurance.getValue().toString().split(", ");
                pr.setString(1, insuranceInfo[0]);
                pr.setString(2, insuranceInfo[1]);
                ResultSet rs = pr.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
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
