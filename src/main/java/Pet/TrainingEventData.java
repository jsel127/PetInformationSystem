package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Stores individual data entries for the Event Table.
 *
 * @version March 5, 2025
 * @author Jasmine Sellers
 */
public class TrainingEventData {
    private final IntegerProperty myEventID;
    private final StringProperty myTrainingType;
    private final IntegerProperty myExecutionTime;
    private final IntegerProperty myCommandHeldTime;
    private final StringProperty myNotes;

    public TrainingEventData(Integer theEventID, String theTrainingType, Integer theExecutionTime, Integer theCommandHeldTime, String theNotes) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myTrainingType = new SimpleStringProperty(theTrainingType);
        myExecutionTime = new SimpleIntegerProperty(theExecutionTime);
        myCommandHeldTime = new SimpleIntegerProperty(theCommandHeldTime);
        myNotes = new SimpleStringProperty(theNotes);
    }

    public int getMyEventID() {
        return myEventID.get();
    }

    public IntegerProperty myEventIDProperty() {
        return myEventID;
    }

    public void setMyEventID(int myEventID) {
        this.myEventID.set(myEventID);
    }

    public String getMyTrainingType() {
        return myTrainingType.get();
    }

    public StringProperty myTrainingTypeProperty() {
        return myTrainingType;
    }

    public void setTrainingType(String myStartDateTime) {
        this.myTrainingType.set(myStartDateTime);
    }

    public int getMyExecutionTime() {
        return myExecutionTime.get();
    }

    public IntegerProperty myExecutionTime() {
        return myExecutionTime;
    }

    public void setMyExecutionTime(int myExecutionTime) {
        this.myExecutionTime.set(myExecutionTime);
    }

    public int getMyCommandHeldTime() {
        return myCommandHeldTime.get();
    }

    public IntegerProperty myCommandHeldTime() {
        return myCommandHeldTime;
    }

    public void setMyCommandHeldTime(int myCommandHeldTime) {
        this.myCommandHeldTime.set(myCommandHeldTime);
    }

    public String getMyNotes() {
        return myNotes.get();
    }

    public StringProperty myNotes() {
        return myNotes;
    }

    public void setMyNotes(String myNotes) {
        this.myNotes.set(myNotes);
    }
}
