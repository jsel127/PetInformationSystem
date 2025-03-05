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
public class EventData {
    private final IntegerProperty myEventID;
    private final StringProperty myStartDateTime;
    private final StringProperty myEndDateTime;

    public EventData(Integer theEventID, String theStartDateTime, String theEndDateTime) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myStartDateTime = new SimpleStringProperty(theStartDateTime);
        myEndDateTime = new SimpleStringProperty(theEndDateTime);
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

    public String getMyStartDateTime() {
        return myStartDateTime.get();
    }

    public StringProperty myStartDateTimeProperty() {
        return myStartDateTime;
    }

    public void setMyStartDateTime(String myStartDateTime) {
        this.myStartDateTime.set(myStartDateTime);
    }

    public String getMyEndDateTime() {
        return myEndDateTime.get();
    }

    public StringProperty myEndDateTimeProperty() {
        return myEndDateTime;
    }

    public void setMyEndDateTime(String myEndDateTime) {
        this.myEndDateTime.set(myEndDateTime);
    }
}
