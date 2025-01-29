package controller;

import Model.Employees;
import Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public class AjoutEmployeController {

    private Employees currentEmployee = null;

    @FXML
    private TableView<Employees> employeTable;

    @FXML
    private DatePicker hireDateField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    private ObservableList<Employees> employeList = FXCollections.observableArrayList();

    private static int idCounter = 1;

    @FXML
    public void addOrUpdateEmployee() {
        // Récupérer les données du formulaire
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String department = departmentField.getText().trim();
        String position = positionField.getText().trim();
        String statusString = statusField.getText().trim();  // C'est une chaîne
        LocalDate hireDate = hireDateField.getValue();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validation des champs
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || department.isEmpty() || position.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
            return;
        }
        if (hireDate == null) {
            showAlert("Erreur", "La date d'embauche est obligatoire", Alert.AlertType.ERROR);
            return;
        }

        // Validation du statut en utilisant l'enum
        Status status;
        try {
            status = Status.fromString(statusString);  // Convertir la chaîne en Status
        } catch (IllegalArgumentException e) {
            showAlert("Erreur", "Statut invalide. Choisissez parmi 'Actif', 'En Congé', ou 'Inactif'.", Alert.AlertType.ERROR);
            return;
        }

        // Validation de l'email avec regex
        if (!isValidEmail(email)) {
            showAlert("Erreur", "Email invalide", Alert.AlertType.ERROR);
            return;
        }



        // Si c'est un nouvel employé
        if (currentEmployee == null) {
            int generatedId = idCounter++;  // L'ID est généré automatiquement
            Employees newEmployee = new Employees(
                    generatedId,
                    firstName,
                    lastName,
                    email,
                    department,
                    position,
                    status,  // Utilisation de l'ENUM status
                    hireDate.toString(),
                    address,
                    phone
            );
            employeList.add(newEmployee);  // Ajouter le nouvel employé à la liste observable
        } else {
            // Si c'est un employé existant, on le met à jour
            boolean found = false;
            for (int i = 0; i < employeList.size(); i++) {
                Employees emp = employeList.get(i);
                if (emp.getId() == currentEmployee.getId()) {  // Comparaison avec l'ID
                    // Mise à jour des informations de l'employé
                    emp.setFirstName(firstName);
                    emp.setLastName(lastName);
                    emp.setEmail(email);
                    emp.setDepartment(department);
                    emp.setPosition(position);
                    emp.setStatus(String.valueOf(status));  // Mise à jour avec l'ENUM status
                    emp.setHireDate(hireDate.toString());
                    emp.setAddress(address);
                    emp.setPhone(phone);

                    // Mise à jour dans la liste observable
                    employeList.set(i, emp);
                    found = true;
                    break;
                }
            }
            if (!found) {
                showAlert("Erreur", "L'employé à modifier n'existe pas.", Alert.AlertType.ERROR);
            }
        }

        // Mise à jour de la TableView
        if (employeTable != null) {
            employeTable.setItems(employeList);  // Lier la TableView à la liste observable
            employeTable.refresh();  // Rafraîchir la TableView après l'ajout ou la modification
        } else {
            System.out.println("La TableView des employés n'est pas initialisée.");
        }

        // Vider les champs après l'opération
        clearFormFields();
    }

    // Méthode pour charger un employé à modifier
    public void loadEmployeeData(Employees employee) {
        this.currentEmployee = employee;
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        departmentField.setText(employee.getDepartment());
        positionField.setText(employee.getPosition());
        statusField.setText(employee.getStatus().name());
        hireDateField.setValue(LocalDate.parse(employee.getHireDate()));
        addressField.setText(employee.getAddress());
        phoneField.setText(employee.getPhone());
    }

    private void clearFormFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        departmentField.clear();
        positionField.clear();
        statusField.clear();
        hireDateField.setValue(null);
        addressField.clear();
        phoneField.clear();
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validation de l'email (utilisation d'une expression régulière)
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Validation du téléphone (format basique, ajustable selon les besoins)

    public void setEmployeList(ObservableList<Employees> employeList) {
        this.employeList = employeList;
    }

    private boolean modified = false;

    public boolean isModified() {
        return modified;
    }
}
