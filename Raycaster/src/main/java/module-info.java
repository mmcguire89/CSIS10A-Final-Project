module com.michael.raycaster {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.michael.raycaster to javafx.fxml;
    exports com.michael.raycaster;
}