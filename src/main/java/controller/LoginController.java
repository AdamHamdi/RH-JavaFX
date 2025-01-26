package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    // Méthode appelée lors du clic sur "Se connecter"
    @FXML
    private void handleLogin() {
        // Exemple de validation de connexion
        String email = emailField.getText();
        String password = passwordField.getText();

        if (isLoginSuccessful(email, password)) {
            redirectToEmployeView();
        } else {
            System.out.println("Échec de la connexion : Email ou mot de passe incorrect.");
        }
    }

    // Méthode simulée pour vérifier si la connexion est réussie
    private boolean isLoginSuccessful(String email, String password) {
        // Remplacez cette logique par une vérification réelle
        return "admin@test.com".equals(email) && "password".equals(password);
    }

    // Redirection vers la page "employe-view.fxml"
    private void redirectToEmployeView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employe-view.fxml"));
            Parent employeView = loader.load();

            // Obtenir la fenêtre actuelle depuis le bouton
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(employeView));
            stage.setTitle("Liste des Employés");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de employe-view.fxml");
        }
    }
}
