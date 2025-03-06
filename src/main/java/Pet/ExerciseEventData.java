package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExerciseEventData {
    private final StringProperty myExerciseName;
    private final IntegerProperty myTotalRepitionsOfExercise;
    private final IntegerProperty myAverageIntensity;

    public ExerciseEventData(String theExerciseName, int theTotalRepitionsOfExercise, int theAverageIntensity) {
        myExerciseName = new SimpleStringProperty(theExerciseName);
        myTotalRepitionsOfExercise = new SimpleIntegerProperty(theTotalRepitionsOfExercise);
        myAverageIntensity = new SimpleIntegerProperty(theAverageIntensity);
    }

    public String getMyExerciseName() {
        return myExerciseName.get();
    }

    public StringProperty ExerciseNameProperty() {
        return myExerciseName;
    }

    public void setExerciseNameProperty(String myExerciseName) {
        this.myExerciseName.set(myExerciseName);
    }

    public int getMyTotalRepitionsOfExercise() {
        return myTotalRepitionsOfExercise.get();
    }

    public IntegerProperty TotalRepitionsOfExerciseProperty() {
        return myTotalRepitionsOfExercise;
    }
    public void TotalRepitionsOfExerciseProperty(int myTotalRepitionsOfExercise) {
        this.myTotalRepitionsOfExercise.set(myTotalRepitionsOfExercise);
    }

    public int getMyAverageIntensity() {
        return myAverageIntensity.get();
    }

    public IntegerProperty AverageIntensityProperty() {
        return myAverageIntensity;
    }

    public void AverageIntensityProperty(int myAverageIntensity) {
        this.myAverageIntensity.set(myAverageIntensity);
    }

}
