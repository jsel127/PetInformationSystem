package Pet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicalCheckupEventData {
    private final IntegerProperty myEventID;
    private final IntegerProperty myVeterinarianID;
    private final StringProperty myVeterinarian;
    private final IntegerProperty myVeterinaryID;
    private final StringProperty myVeterinary;
    private final StringProperty myWeight;
    private final StringProperty myNotes;
    public MedicalCheckupEventData(Integer theEventID, Integer theVeterinarianID, String theVeterinarian,
                                   Integer theVeterinaryID, String theVeterinary, String theWeight, String theNotes) {
        myEventID = new SimpleIntegerProperty(theEventID);
        myVeterinarianID = new SimpleIntegerProperty(theVeterinarianID);
        myVeterinarian = new SimpleStringProperty(theVeterinarian);
        myVeterinaryID = new SimpleIntegerProperty(theVeterinaryID);
        myVeterinary = new SimpleStringProperty(theVeterinary);
        myWeight = new SimpleStringProperty(theWeight);
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

    public int getMyVeterinarianID() {
        return myVeterinarianID.get();
    }

    public IntegerProperty myVeterinarianIDProperty() {
        return myVeterinarianID;
    }

    public void setMyVeterinarianID(int myVeterinarianID) {
        this.myVeterinarianID.set(myVeterinarianID);
    }

    public String getMyVeterinarian() {
        return myVeterinarian.get();
    }

    public StringProperty myVeterinarianProperty() {
        return myVeterinarian;
    }

    public void setMyVeterinarian(String myVeterinarian) {
        this.myVeterinarian.set(myVeterinarian);
    }

    public int getMyVeterinaryID() {
        return myVeterinaryID.get();
    }

    public IntegerProperty myVeterinaryIDProperty() {
        return myVeterinaryID;
    }

    public void setMyVeterinaryID(int myVeterinaryID) {
        this.myVeterinaryID.set(myVeterinaryID);
    }

    public String getMyVeterinary() {
        return myVeterinary.get();
    }

    public StringProperty myVeterinaryProperty() {
        return myVeterinary;
    }

    public void setMyVeterinary(String myVeterinary) {
        this.myVeterinary.set(myVeterinary);
    }

    public String getMyWeight() {
        return myWeight.get();
    }

    public StringProperty myWeightProperty() {
        return myWeight;
    }

    public void setMyWeight(String myWeight) {
        this.myWeight.set(myWeight);
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
