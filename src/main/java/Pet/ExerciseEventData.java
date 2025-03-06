package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExerciseEventData {
    private final IntegerProperty myEventID;

    private final StringProperty myExerciseType;
    private final IntegerProperty myIntensityLevel;
    private final StringProperty myDistance;

    public ExerciseEventData(Integer theEventID, String theEventType, Integer theIntensityLevel, String theDistance) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myExerciseType = new SimpleStringProperty(theEventType);
        myIntensityLevel = new SimpleIntegerProperty(theIntensityLevel);
        myDistance = new SimpleStringProperty(theDistance);
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

    public String getMyExerciseType() {
        return myExerciseType.get();
    }

    public StringProperty myExerciseTypeProperty() {
        return myExerciseType;
    }

    public void setMyExerciseType(String myExerciseType) {
        this.myExerciseType.set(myExerciseType);
    }

    public int getMyIntensityLevel() {
        return myIntensityLevel.get();
    }

    public IntegerProperty myIntensityLevelProperty() {
        return myIntensityLevel;
    }

    public void setMyIntensityLevel(int myIntensityLevel) {
        this.myIntensityLevel.set(myIntensityLevel);
    }

    public String getMyDistance() {
        return myDistance.get();
    }

    public StringProperty myDistanceProperty() {
        return myDistance;
    }

    public void setMyDistance(String myDistance) {
        this.myDistance.set(myDistance);
    }
}
