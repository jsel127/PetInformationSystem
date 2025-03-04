package Owner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OwnershipData {
    private final StringProperty myPetID;
    private final StringProperty myPet;
    private final StringProperty myOwnershipType;
    private final StringProperty myDateOfAdoption;

    public OwnershipData(String thePetID, String thePet, String theOwnershipType, String theDateOfAdoption) {
        myPetID = new SimpleStringProperty(thePetID);
        myPet = new SimpleStringProperty(thePet);
        myOwnershipType = new SimpleStringProperty(theOwnershipType);
        myDateOfAdoption = new SimpleStringProperty(theDateOfAdoption);
    }

    public String getMyPetID() {
        return myPetID.get();
    }

    public StringProperty myPetIDProperty() {
        return myPetID;
    }

    public void setMyPetID(String myPetID) {
        this.myPetID.set(myPetID);
    }

    public String getMyPet() {
        return myPet.get();
    }

    public StringProperty myPetProperty() {
        return myPet;
    }

    public void setMyPet(String myPet) {
        this.myPet.set(myPet);
    }

    public String getMyOwnershipType() {
        return myOwnershipType.get();
    }

    public StringProperty myOwnershipTypeProperty() {
        return myOwnershipType;
    }

    public void setMyOwnershipType(String myOwnership) {
        this.myOwnershipType.set(myOwnership);
    }

    public String getMyDateOfAdoption() {
        return myDateOfAdoption.get();
    }

    public StringProperty myDateOfAdoptionProperty() {
        return myDateOfAdoption;
    }

    public void setMyDateOfAdoption(String myDateOfAdoption) {
        this.myDateOfAdoption.set(myDateOfAdoption);
    }
}
