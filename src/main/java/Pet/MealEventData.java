package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MealEventData {
    private final IntegerProperty myEventID;
    private final StringProperty myMealType;
    private final StringProperty myNotes;

    public MealEventData(Integer theEventID, String theMealType, String theNotes) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myMealType = new SimpleStringProperty(theMealType);
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

    public String getMyMealType() {
        return myMealType.get();
    }

    public StringProperty myMealTypeProperty() {
        return myMealType;
    }

    public void setMyMealType(String myMealType) {
        this.myMealType.set(myMealType);
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
