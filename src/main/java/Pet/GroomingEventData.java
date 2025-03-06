package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroomingEventData {
    private final IntegerProperty myEventID;
    private final StringProperty myGroomingType;
    private final IntegerProperty myGroomerID;

    public GroomingEventData(Integer theEventID, String theGroomingType, Integer theGroomerID) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myGroomingType = new SimpleStringProperty(theGroomingType);
        myGroomerID = new SimpleIntegerProperty(theGroomerID);
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

    public String getMyGroomingType() {
        return myGroomingType.get();
    }

    public StringProperty myGroomingTypeProperty() {
        return myGroomingType;
    }

    public void setMyGroomingType(String myGroomingType) {
        this.myGroomingType.set(myGroomingType);
    }

    public int getMyGroomerID() {
        return myGroomerID.get();
    }

    public IntegerProperty myGroomerIDProperty() {
        return myGroomerID;
    }

    public void setMyGroomerID(int myGroomerID) {
        this.myGroomerID.set(myGroomerID);
    }
}
