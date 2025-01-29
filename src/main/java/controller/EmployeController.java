package controller;

import Model.Employees;
import Model.Status;
import Util.Createdb;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

public class EmployeController {

    @FXML
    private TableView<Employees> employeTable;

    @FXML
    private TableColumn<?, Integer> id;

    @FXML
    private TableColumn<?, String> firstName;

    @FXML
    private TableColumn<?, String> lastName;

    @FXML
    private TableColumn<?, String> department;

    @FXML
    private TableColumn<?, String> email;

    @FXML
    private TableColumn<?, String> position;

    @FXML
    private TableColumn<Employees, String> statusColumn;

    @FXML
    private TableColumn<?, Date> hireDate;

    @FXML
    private TableColumn<?, String> phone;

    @FXML
    private TableColumn<?, String> address;

    @FXML
    private TableColumn<Employees, Void> actions; // Nouvelle colonne pour les boutons

    @FXML
    private TextField searchField;

    private ObservableList<Employees> employeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("id: " + id);
        System.out.println("firstName: " + firstName);
        System.out.println("employeTable: " + employeTable);

        // Configure the columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getStatus()));
        hireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Configure the actions column
        addActionsToTable();

        // Load the employee data
        loadEmployees();
        employeTable.setItems(employeList);

        // Set listener for search field to filter employees
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchEmploye());
    }

    private void loadEmployees() {
        String query = "SELECT * FROM employees"; // Adapte la requête à ta table

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ressources_humaines", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String statusString = resultSet.getString("status");

                Status status;
                try {
                    status = Status.fromString(statusString);  // Utiliser la méthode fromString pour la conversion
                } catch (IllegalArgumentException e) {
                    showAlert("Erreur", "Statut invalide. Choisissez parmi 'Actif', 'En Congé', ou 'Inactif'.", Alert.AlertType.ERROR);
                    return;  // Retourner pour éviter un employé avec un statut invalide
                }
                Employees employee = new Employees(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("department"),
                        resultSet.getString("position"),
                         status,
                        resultSet.getString("hireDate"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );

                employeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addEmploye() {
        try {
            // Charger le fichier FXML de l'ajout d'employé
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ajout-employe.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la fenêtre ajout-employé
            AjoutEmployeController ajoutEmployeController = fxmlLoader.getController();

            // Partager la liste employeList avec le contrôleur du formulaire
            ajoutEmployeController.setEmployeList(employeList);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Ajouter un employé");
            Scene scene = new Scene(root, 800, 250);
            scene.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm()
            );
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchEmploye() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<Employees> filteredList = FXCollections.observableArrayList();

        for (Employees employee : employeList) { // Utiliser employeList au lieu de Createdb.getEmployes()
            if (employee.getFirstName().toLowerCase().contains(searchQuery) ||
                    employee.getPosition().toLowerCase().contains(searchQuery)) {
                filteredList.add(employee);
            }
        }
        employeTable.setItems(filteredList);
    }

    private void addActionsToTable() {

        Callback<TableColumn<Employees, Void>, TableCell<Employees, Void>> cellFactory = param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox actionBox = new HBox(editButton, deleteButton);

            {
                // Espacement entre les boutons
                actionBox.setSpacing(10);
                editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");

                // Action du bouton Modifier
                editButton.setOnAction(event -> {
                    Employees employee = getTableView().getItems().get(getIndex());
                    editEmployee(employee);
                });

                // Action du bouton Supprimer
                deleteButton.setOnAction(event -> {
                    Employees employee = getTableView().getItems().get(getIndex());
                    deleteEmployee(employee);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        };

        // Appliquer le cellFactory à la colonne
        actions.setCellFactory(cellFactory);
    }


    private void editEmployee(Employees employee) {
        System.out.println("Modifier : " + employee);
        // Charger le formulaire d'ajout avec les données de l'employé
        try {
            // Charger le fichier FXML de l'ajout d'employé
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ajout-employe.fxml"));
            Parent root = fxmlLoader.load();

            // Passer l'employé sélectionné au contrôleur
            AjoutEmployeController ajoutController = fxmlLoader.getController();
            ajoutController.loadEmployeeData(employee); // Charger les données dans le formulaire

            // Afficher la fenêtre de modification
            Stage stage = new Stage();
            stage.setTitle("Modifier un employé");
            stage.setScene(new Scene(root));
            stage.show();

            // Après modification, mettre à jour la TableView
            stage.setOnHiding(event -> {
                if (ajoutController.isModified()) { // Vérifier si l'employé a été modifié
                    employeTable.refresh(); // Rafraîchir la TableView pour refléter les changements
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteEmployee(Employees employee) {
        System.out.println("Supprimer : " + employee);
        employeTable.getItems().remove(employee);
        // Implémentez votre logique de suppression ici (ex. supprimer de la base de données)
    }

}
