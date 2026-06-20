module com.example.fitpro {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.fitpro.controller to javafx.fxml;
    opens com.example.fitpro to javafx.fxml;

    exports com.example.fitpro;
    exports com.example.fitpro.controller;
    exports com.example.fitpro.model;
    opens com.example.fitpro.model to javafx.fxml;
}