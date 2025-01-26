package controller;

import Model.Employees;
import Util.Createdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeController {

    @FXML
    private TableView<Employees> employeTable;

    @FXML
    private TableColumn<Employees, Integer> id;

    @FXML
    private TableColumn<Employees, String> firstName;

    @FXML
    private TableColumn<Employees, String> lastName;

    @FXML
    private TableColumn<Employees, String> department;

    @FXML
    private TableColumn<Employees, String> email;

    @FXML
    private TableColumn<Employees, String> positionColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Employees> employeList;

    @FXML
    public void initialize() {
        // Configure the columns
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file.";
        assert department != null : "fx:id=\"department\" was not injected: check your FXML file.";
        assert positionColumn != null : "fx:id=\"positionColumn\" was not injected: check your FXML file.";

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
       //
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        // Load the employee data
        loadEmployees();

        // Set listener for search field to filter employees
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchEmploye());
    }

    private void loadEmployees() {
        employeList = FXCollections.observableArrayList(Createdb.getEmployes());
        employeTable.setItems(employeList);
    }

    @FXML
    private void searchEmploye() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<Employees> filteredList = FXCollections.observableArrayList();

        for (Employees employee : Createdb.getEmployes()) {
            if (employee.getFirstName().toLowerCase().contains(searchQuery) ||
                    employee.getPosition().toLowerCase().contains(searchQuery)) {
                filteredList.add(employee);
            }
        }
        employeTable.setItems(filteredList);
    }

    @FXML
    private void addEmploye() {
        // Logic to add a new employee (not implemented here)
        System.out.println("Add Employee button clicked!");
        // This would usually open a form to add employee details,
        // then add the employee to the database and reload the table.
    }
}
