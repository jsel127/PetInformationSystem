package Owner;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {
    private int myUserID;
    private dbConnection myConnection;
    @FXML
    private ComboBox myExpenseType;
    @FXML
    private TextField myAmount;
    @FXML
    private DatePicker myDateOfExpense;
    @FXML
    private TextArea myNotes;
    @FXML
    private Label myErrorMessage;
    @FXML
    private Button myReturnBtn;

    @FXML
    private TableView<ExpenseData> myExpenseTable;
    @FXML
    private TableColumn<ExpenseData, Integer> myExpenseIDCol;
    @FXML
    private TableColumn<ExpenseData, String> myExpenseTypeCol;
    @FXML
    private TableColumn<ExpenseData, String> myAmountCol;
    @FXML
    private TableColumn<ExpenseData, String> myDateCol;
    @FXML
    private TableColumn<ExpenseData, String> myNotesCol;

    private ObservableList<ExpenseData> myExpenseData;

    public void initialize(URL theURL, ResourceBundle theRB) {
        myConnection = new dbConnection();
        initializeExpenseTypeComboBox();
    }

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }

    private void initializeExpenseTypeComboBox() {
        String query = "SELECT ExpenseType FROM ExpenseTypes";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            ObservableList<String> species = FXCollections.observableArrayList();
            while (rs.next()) {
                species.add(rs.getString(1));
            }
            myExpenseType.setItems(species);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void returnToOwnerDashboard(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myReturnBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Owner/owner.fxml").openStream());

            OwnerController ownerController = (OwnerController) loader.getController();
            ownerController.setUserID(myUserID);
            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Owner Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadExpenseData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myExpenseData = FXCollections.observableArrayList();

            String query = "SELECT ExpenseID, ExpenseTypes.ExpenseType, Amount, `Date`, Notes " +
                           "FROM Expenses JOIN ExpenseTypes ON Expenses.ExpenseTypeID = ExpenseTypes.ExpenseTypeID " +
                           "WHERE Expenses.UserID = ? LIMIT 50;";
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1, myUserID);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                myExpenseData.add(new ExpenseData(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        myExpenseIDCol.setCellValueFactory(new PropertyValueFactory<ExpenseData, Integer>("myExpenseID"));
        myExpenseTypeCol.setCellValueFactory(new PropertyValueFactory<ExpenseData, String>("myExpenseType"));
        myAmountCol.setCellValueFactory(new PropertyValueFactory<ExpenseData, String>("myAmount"));
        myDateCol.setCellValueFactory(new PropertyValueFactory<ExpenseData, String>("myDateOfExpense"));
        myNotesCol.setCellValueFactory(new PropertyValueFactory<ExpenseData, String>("myNotes"));

        myExpenseTable.setItems(null);
        myExpenseTable.setItems(myExpenseData);
    }
    @FXML
    public void clearForm(ActionEvent theEvent) {
        myExpenseType.setValue(null);
        myAmount.clear();
        myDateOfExpense.setValue(null);
        myNotes.clear();
    }

    @FXML
    public void addExpense(ActionEvent theEvent) {
        String insertStatement = "INSERT INTO Expenses(UserID, ExpenseTypeID, Amount, `Date`, Notes) VALUES(?, ?, ?, ?, ?);";
        try {
            if (myAmount.getText() == null) {
                myErrorMessage.setText("An amount must be specified.");
                return;
            }
            if (myExpenseType.getValue() == null) {
                myErrorMessage.setText("An expense type must be selected.");
                return;
            }
            if (myDateOfExpense.getValue() == null) {
                myErrorMessage.setText("A date of expense must be specified.");
                return;
            }
            Connection conn = dbConnection.getConnection();
            PreparedStatement prInsert = conn.prepareStatement(insertStatement);
            prInsert.setInt(1, myUserID);
            prInsert.setInt(2, getExpenseTypeID(myExpenseType.getValue().toString()));
            prInsert.setBigDecimal(3, new BigDecimal(myAmount.getText()));
            prInsert.setDate(4, Date.valueOf(myDateOfExpense.getValue()));
            prInsert.setObject(5, myNotes.getText());
            System.out.println(prInsert.toString());
            prInsert.execute();
            conn.close();
            myErrorMessage.setText(null);
        } catch (SQLException ex) {
            myErrorMessage.setText("Expense could not be added. Please check that valid data has been entered. ");
        }
    }

    private int getExpenseTypeID(String theExpenseType) {
        String getOwnershipTypeId = "SELECT ExpenseTypeID FROM ExpenseTypes WHERE ExpenseType = ?;";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement prOwnershipType = conn.prepareStatement(getOwnershipTypeId);
            prOwnershipType.setString(1, theExpenseType);
            ResultSet rsOwnershipType = prOwnershipType.executeQuery();
            if (rsOwnershipType.next()) {
                return rsOwnershipType.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1;
    }
}
