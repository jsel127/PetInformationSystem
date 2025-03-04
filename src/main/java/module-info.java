module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens PetInformationSystem to javafx.fxml;
    exports PetInformationSystem;
}