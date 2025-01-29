package Model;

public class Employees {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private Status status;
    private String hireDate;
    private String address;
    private String phone;

    // Constructeur
    public Employees(int id, String firstName, String lastName, String email, String department, String position, Status status, String hireDate, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.status = status;
        this.hireDate = hireDate;
        this.address = address;
        this.phone = phone;
    }


    // Getters pour les colonnes de la TableView
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
    }

    public void setLastName(String lastName) {
    }

    public void setEmail(String email) {
    }

    public void setDepartment(String department) {
    }

    public void setPosition(String position) {
    }

    public void setStatus(String status) {
    }

    public void setHireDate(String string) {
    }

    public void setAddress(String address) {
    }

    public void setPhone(String phone) {
    }


}
