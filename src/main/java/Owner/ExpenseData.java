package Owner;

import javafx.beans.property.*;

public class ExpenseData {
    private final IntegerProperty myExpenseID;
    private final StringProperty myExpenseType;
    private final StringProperty myAmount;
    private final StringProperty myDateOfExpense;
    private final StringProperty myNotes;

    public ExpenseData(int theExpenseID, String theExpenseType, String theAmount, String theDateOfExpense, String theNotes) {
        myExpenseID = new SimpleIntegerProperty(theExpenseID);
        myExpenseType = new SimpleStringProperty(theExpenseType);
        myAmount = new SimpleStringProperty(theAmount);
        myDateOfExpense = new SimpleStringProperty(theDateOfExpense);
        myNotes = new SimpleStringProperty(theNotes);
    }

    public int getMyExpenseID() {
        return myExpenseID.get();
    }

    public IntegerProperty myExpenseIDProperty() {
        return myExpenseID;
    }

    public void setMyExpenseID(int myExpenseID) {
        this.myExpenseID.set(myExpenseID);
    }

    public String getMyExpenseType() {
        return myExpenseType.get();
    }

    public StringProperty myExpenseTypeProperty() {
        return myExpenseType;
    }

    public void setMyExpenseType(String myExpenseType) {
        this.myExpenseType.set(myExpenseType);
    }

    public String getMyAmount() {
        return myAmount.get();
    }

    public StringProperty myAmountProperty() {
        return myAmount;
    }

    public void setMyAmount(String myAmount) {
        this.myAmount.set(myAmount);
    }

    public String getMyDateOfExpense() {
        return myDateOfExpense.get();
    }

    public StringProperty myDateOfExpenseProperty() {
        return myDateOfExpense;
    }

    public void setMyDateOfExpense(String myDateOfExpense) {
        this.myDateOfExpense.set(myDateOfExpense);
    }

    public String getMyNotes() {
        return myNotes.get();
    }

    public StringProperty myNotesProperty() {
        return myNotes;
    }

    public void setMyNotes(String myNotes) {
        this.myNotes.set(myNotes);
    }
}
