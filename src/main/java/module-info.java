module com.company.jarodistance {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.company.myapp to javafx.fxml;
    exports com.company.myapp;
    exports com.company.myapp.controller;
    opens com.company.myapp.controller to javafx.fxml;
}