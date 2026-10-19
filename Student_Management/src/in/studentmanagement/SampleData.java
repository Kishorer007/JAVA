
package in.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SampleData {

    public static void insertStudents() throws SQLException {

        String sql = "INSERT IGNORE INTO students "
                   + "(name, age, course, email) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String[][] students = {
                {"Arun Kumar", "22", "Java", "arun@gmail.com"},
                {"Priya", "21", "Python", "priya@gmail.com"},
                {"Rahul", "23", "Full Stack", "rahul@gmail.com"},
                {"Divya", "22", "SQL", "divya@gmail.com"},
                {"Karthik", "24", "Java", "karthik@gmail.com"}
            };

            for (String[] student : students) {
                ps.setString(1, student[0]);
                ps.setInt(2, Integer.parseInt(student[1]));
                ps.setString(3, student[2]);
                ps.setString(4, student[3]);

                ps.addBatch();
            }

            int[] results = ps.executeBatch();

            System.out.println(
                "Sample students inserted: " + results.length);
        }
    }

    public static void main(String[] args) {
        try {
            DBConnection.initializeDatabase();
            insertStudents();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
