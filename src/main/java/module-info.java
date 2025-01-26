module com.example.ressources_humaines {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    opens controller to javafx.fxml;
    exports controller;
    opens com.example.ressources_humaines to javafx.fxml;
    opens Model to javafx.base;
    exports Model;
    exports com.example.ressources_humaines;
}