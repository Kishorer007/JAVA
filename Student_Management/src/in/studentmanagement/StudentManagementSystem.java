
package in.studentmanagement;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {

        try {
            DBConnection.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
            return;
        }

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;

                    case 2:
                        viewStudents();
                        break;

                    case 3:
                        searchStudent();
                        break;

                    case 4:
                        updateStudent();
                        break;

                    case 5:
                        deleteStudent();
                        break;

                    case 0:
                        System.out.println("Thank you!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Database operation failed: "
                        + e.getMessage());
            }
        }
    }

    private static void addStudent() throws SQLException {

        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter age: ");
        int age = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter course: ");
        String course = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || email.isEmpty()
                || age <= 0) {
            System.out.println("Enter valid student details.");
            return;
        }

        Student student = new Student(name, age, course, email);

        if (dao.addStudent(student)) {
            System.out.println("Student added successfully!");
        }
    }

    private static void viewStudents() throws SQLException {

        List<Student> students = dao.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() throws SQLException {

        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Student student = dao.getStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(student);
        }
    }

    private static void updateStudent() throws SQLException {

        System.out.print("Enter student ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (dao.getStudentById(id) == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter new age: ");
        int age = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter new course: ");
        String course = sc.nextLine().trim();

        System.out.print("Enter new email: ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || email.isEmpty()
                || age <= 0) {
            System.out.println("Enter valid student details.");
            return;
        }

        Student student =
                new Student(id, name, age, course, email);

        if (dao.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student was not updated.");
        }
    }

    private static void deleteStudent() throws SQLException {

        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (dao.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }
}
