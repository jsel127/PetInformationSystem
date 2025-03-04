module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens Login to javafx.fxml;
    opens Owner to javafx.fxml;
    opens Veterinarian to javafx.fxml;
    opens Caretaker to javafx.fxml;

    exports Login;
    exports Owner;
    exports Veterinarian;
    exports Caretaker;
}