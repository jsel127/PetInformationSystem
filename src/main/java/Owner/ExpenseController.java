package Owner;

import Database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Button myAddExpenseBtn;
    @FXML
    private Button myLoadTableBtn;
    @FXML
    private Label myErrorMessage;

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

    }

    @FXML
    public void loadExpenseData(ActionEvent theEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            myExpenseData = FXCollections.observableArrayList();

            String query = "SELECT ExpenseID, ExpenseTypes.ExpenseType, Amount, `Date`, Notes " +
                           "FROM Expenses JOIN ExpenseTypes ON Expenses.ExpenseTypeID = ExpenseTypes.ExpenseTypeID " +
                           "WHERE Expenses.UserID = ?;";
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

    public void setUserID(int theUserID) {
        myUserID = theUserID;
    }
}
