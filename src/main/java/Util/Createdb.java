package Util;

import Model.Employees;
import Model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Createdb {

    private static final String URL = "jdbc:mysql://localhost:3306/ressources_humaines";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static List<Employees> getEmployes() {
        List<Employees> employes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                String statusString = rs.getString("status").toUpperCase();

                // Essayer de convertir la chaîne en enum Status
                Status status = Status.valueOf(statusString);
                try {
                    status = Status.valueOf(statusString);  // Cela convertira le String en un enum
                } catch (IllegalArgumentException e) {
                    status = Status.ACTIF;  // Valeur par défaut si le statut est invalide
                }
                boolean add = employes.add(new Employees(rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getString("hireDate"),
                        status,
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }

    public static List<Employees> searchEmployes(String query) {
        // Implémentation similaire avec une requête SQL filtrée
        return new ArrayList<>();
    }
    public static boolean updateEmployee(Employees employee) {
        String query = "UPDATE employees SET firstName = ?, lastName = ?, email = ?, department = ?, position = ?, status = ?, hireDate = ?, address = ?, phone = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setString(5, employee.getPosition());
            pstmt.setString(6, employee.getStatus().name());

            pstmt.setString(7, employee.getHireDate());
            pstmt.setString(8, employee.getAddress());
            pstmt.setString(9, employee.getPhone());
            pstmt.setInt(10, employee.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // même remarque que pour l'ajout
            return false;
        }
    }
    public static boolean addEmployee(Employees employee) {
        String query = "INSERT INTO employees (firstName, lastName , position , department, hireDate, status, email , phone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(5, employee.getPosition());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setString(7, employee.getHireDate());
            pstmt.setString(6, employee.getStatus().name());

            pstmt.setString(3, employee.getEmail());
            pstmt.setString(9, employee.getPhone());
            pstmt.setString(8, employee.getAddress());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // tu peux aussi gérer l'erreur de façon plus élégante
            return false;
        }
    }

}
