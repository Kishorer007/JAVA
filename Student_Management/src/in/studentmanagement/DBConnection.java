
package in.studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/student_db";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void initializeDatabase() throws SQLException {

        try (Connection con = DriverManager.getConnection(
                    SERVER_URL, USER, PASSWORD);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS student_db");
        }

        try (Connection con = DriverManager.getConnection(
                    DATABASE_URL, USER, PASSWORD);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL, " +
                "course VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) NOT NULL UNIQUE)"
            );
        }

        System.out.println("Database and table are ready.");
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                DATABASE_URL, USER, PASSWORD);
    }
}
