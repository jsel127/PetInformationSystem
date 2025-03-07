package Login;

public enum ratingType {
    Groomer, Veterinarian, Veterinary, CareTaker, DayCare;
    private ratingType() {}

    public String value() {
        return name();
    }

    public static ratingType fromvalue(String theValue) {
        return valueOf(theValue);
    }
}
