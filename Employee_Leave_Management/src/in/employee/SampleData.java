
package in.employee;

import java.sql.SQLException;

public class SampleData {

    public static void main(String[] args) {

        try {
            DBConnection.initializeDatabase();

            LeaveDAO dao = new LeaveDAO();

            addEmployee(dao, "Arun Kumar",
                    "arun@gmail.com", "IT");

            addEmployee(dao, "Priya",
                    "priya@gmail.com", "HR");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addEmployee(
            LeaveDAO dao, String name,
            String email, String department)
            throws SQLException {

        try {
            dao.registerEmployee(
                    new Employee(name, email, department));

            System.out.println(name + " registered successfully.");

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println(name + " already exists.");
        }
    }
}
