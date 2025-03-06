package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PottyEventData {
    private final IntegerProperty myEventID;
    private final StringProperty myPottyType;
    private final StringProperty myNotes;

    public PottyEventData(Integer theEventID, String thePottyType, String theNotes) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myPottyType = new SimpleStringProperty(thePottyType);
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

    public String getMyPottyType() {
        return myPottyType.get();
    }

    public StringProperty myPottyTypeProperty() {
        return myPottyType;
    }

    public void setMyPottyType(String myPottyType) {
        this.myPottyType.set(myPottyType);
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
