package Login;

public enum userType {
    Owner, Veterinarian, Caretaker;
    private userType() {}

    public String value() {
        return name();
    }

    public static userType fromvalue(String theValue) {
        return valueOf(theValue);
    }
}
