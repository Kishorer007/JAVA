
package in.employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeLeaveManagementSystem {

    private static final Scanner sc = new Scanner(System.in);
    private static final LeaveDAO dao = new LeaveDAO();

    public static void main(String[] args) {

        try {
            DBConnection.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT =====");
            System.out.println("1. Register Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Apply for Leave");
            System.out.println("4. View Leave Requests");
            System.out.println("5. Approve Leave");
            System.out.println("6. Reject Leave");
            System.out.println("7. Check Leave Balance");
            System.out.println("0. Exit");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1:
                        registerEmployee();
                        break;

                    case 2:
                        viewEmployees();
                        break;

                    case 3:
                        applyLeave();
                        break;

                    case 4:
                        viewRequests();
                        break;

                    case 5:
                        processLeave("APPROVED");
                        break;

                    case 6:
                        processLeave("REJECTED");
                        break;

                    case 7:
                        checkBalance();
                        break;

                    case 0:
                        System.out.println("Thank you!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void registerEmployee()
            throws SQLException {

        String name = readText("Enter employee name: ");
        String email = readText("Enter email: ");
        String department = readText("Enter department: ");

        Employee e = new Employee(name, email, department);

        if (dao.registerEmployee(e)) {
            System.out.println("Employee registered successfully!");
        }
    }

    private static void viewEmployees()
            throws SQLException {

        List<Employee> employees = dao.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    private static void applyLeave()
            throws SQLException {

        int employeeId = readInt("Enter employee ID: ");
        int days = readInt("Enter number of leave days: ");
        String reason = readText("Enter reason for leave: ");

        if (dao.applyLeave(employeeId, days, reason)) {
            System.out.println("Leave request submitted successfully!");
        } else {
            System.out.println(
                    "Request failed. Check employee ID and leave balance.");
        }
    }

    private static void viewRequests()
            throws SQLException {

        List<LeaveRequest> requests = dao.getAllRequests();

        if (requests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        for (LeaveRequest request : requests) {
            System.out.println(request);
        }
    }

    private static void processLeave(String decision)
            throws SQLException {

        int requestId = readInt("Enter leave request ID: ");

        if (dao.processRequest(requestId, decision)) {
            System.out.println("Leave request " +
                    decision.toLowerCase() + " successfully!");
        } else {
            System.out.println(
                    "Request could not be processed. " +
                    "Check its ID, status, and available balance.");
        }
    }

    private static void checkBalance()
            throws SQLException {

        int employeeId = readInt("Enter employee ID: ");
        dao.showBalance(employeeId);
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }
}
