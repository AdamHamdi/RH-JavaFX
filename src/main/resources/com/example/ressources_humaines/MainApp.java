package com.example.ressources_humaines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger l'interface employe-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ressources_humaines/view/employe-view.fxml"));
        Parent root = loader.load();

        // Configurer la scène et le stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gestion des Employés");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
