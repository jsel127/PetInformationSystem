package Login;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RatingData {
    private final IntegerProperty myCompanyID;
    private final StringProperty myRatingValue;
    private final StringProperty myComment;

    public RatingData(int theCompanyID, String theRatingValue, String theComment) {
        myCompanyID = new SimpleIntegerProperty(theCompanyID);
        myRatingValue = new SimpleStringProperty(theRatingValue);
        myComment = new SimpleStringProperty(theComment);
    }

    public int getMyCompanyID() {
        return myCompanyID.get();
    }

    public IntegerProperty myCompanyIDProperty() {
        return myCompanyID;
    }

    public void setMyCompanyID(int myExpenseID) {
        this.myCompanyID.set(myExpenseID);
    }

    public String getMyRatingValue() {
        return myRatingValue.get();
    }

    public StringProperty myRatingValueProperty() {
        return myRatingValue;
    }

    public void setMyRatingValue(String myRatingValue) {
        this.myRatingValue.set(myRatingValue);
    }

    public String getMyComment() {
        return myComment.get();
    }

    public StringProperty myCommentProperty() {
        return myComment;
    }

    public void setMyComment(String myComment) {
        this.myComment.set(myComment);
    }

}
