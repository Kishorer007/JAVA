
package in.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306/";

    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/employee_leave_db";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(
                DATABASE_URL, USER, PASSWORD);
    }

    public static void initializeDatabase()
            throws SQLException {

        try (Connection con = DriverManager.getConnection(
                    SERVER_URL, USER, PASSWORD);
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS employee_leave_db");
        }

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) NOT NULL UNIQUE, " +
                "department VARCHAR(100) NOT NULL)"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS leave_balance (" +
                "employee_id INT PRIMARY KEY, " +
                "total_days INT NOT NULL DEFAULT 12, " +
                "used_days INT NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (employee_id) REFERENCES employees(id))"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS leave_requests (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "employee_id INT NOT NULL, " +
                "leave_days INT NOT NULL, " +
                "reason VARCHAR(255) NOT NULL, " +
                "status VARCHAR(20) NOT NULL DEFAULT 'PENDING', " +
                "FOREIGN KEY (employee_id) REFERENCES employees(id))"
            );
        }

        System.out.println("Database and tables are ready.");
    }
}
