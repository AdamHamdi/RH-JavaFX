package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeViewController {

    @FXML
    private Button gestionEmployesButton;

    @FXML
    private Button suiviCongesButton;

    @FXML
    private Button gestionPaieButton;

    @FXML
    private Button evaluationsPerformanceButton;

    @FXML
    private Button gestionFormationsButton;

    @FXML
    private Button rapportsStatistiquesButton;

    @FXML
    private void handleGestionEmployes() {
        System.out.println("Gestion des Employés clicked");
        // Add your navigation or logic here
    }

    @FXML
    private void handleSuiviConges() {
        System.out.println("Suivi des Congés et Absences clicked");
        // Add your navigation or logic here
    }

    @FXML
    private void handleGestionPaie() {
        System.out.println("Gestion de la Paie clicked");
        // Add your navigation or logic here
    }

    @FXML
    private void handleEvaluationsPerformance() {
        System.out.println("Évaluations de Performance clicked");
        // Add your navigation or logic here
    }

    @FXML
    private void handleGestionFormations() {
        System.out.println("Gestion des Formations clicked");
        // Add your navigation or logic here
    }

    @FXML
    private void handleRapportsStatistiques() {
        System.out.println("Rapports et Statistiques RH clicked");
        // Add your navigation or logic here
    }
}
