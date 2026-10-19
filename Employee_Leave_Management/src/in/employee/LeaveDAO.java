
package in.employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    // Register an employee and initialize leave balance
    public boolean registerEmployee(Employee e)
            throws SQLException {

        String sql = "INSERT INTO employees " +
                "(name, email, department) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int employeeId;

                try (PreparedStatement ps = con.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, e.getName());
                    ps.setString(2, e.getEmail());
                    ps.setString(3, e.getDepartment());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        employeeId = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO leave_balance " +
                        "(employee_id, total_days, used_days) " +
                        "VALUES (?, 12, 0)")) {

                    ps.setInt(1, employeeId);
                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // View all employees
    public List<Employee> getAllEmployees()
            throws SQLException {

        List<Employee> list = new ArrayList<>();

        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department")
                ));
            }
        }

        return list;
    }

    // Apply for leave
    public boolean applyLeave(int employeeId, int days,
                              String reason)
            throws SQLException {

        String balanceSql =
                "SELECT total_days, used_days FROM leave_balance " +
                "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int total;
                int used;

                try (PreparedStatement ps =
                        con.prepareStatement(balanceSql)) {

                    ps.setInt(1, employeeId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        total = rs.getInt("total_days");
                        used = rs.getInt("used_days");
                    }
                }

                if (days <= 0 || days > total - used) {
                    con.rollback();
                    return false;
                }

                String sql =
                        "INSERT INTO leave_requests " +
                        "(employee_id, leave_days, reason) " +
                        "VALUES (?, ?, ?)";

                try (PreparedStatement ps =
                        con.prepareStatement(sql)) {

                    ps.setInt(1, employeeId);
                    ps.setInt(2, days);
                    ps.setString(3, reason);
                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // View all leave requests
    public List<LeaveRequest> getAllRequests()
            throws SQLException {

        List<LeaveRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM leave_requests ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LeaveRequest(
                    rs.getInt("id"),
                    rs.getInt("employee_id"),
                    rs.getInt("leave_days"),
                    rs.getString("reason"),
                    rs.getString("status")
                ));
            }
        }

        return list;
    }

    // Approve or reject a pending request
    public boolean processRequest(int requestId,
                                  String decision)
            throws SQLException {

        if (!decision.equals("APPROVED")
                && !decision.equals("REJECTED")) {
            throw new IllegalArgumentException(
                    "Invalid decision.");
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int employeeId;
                int days;

                String findSql =
                        "SELECT employee_id, leave_days " +
                        "FROM leave_requests " +
                        "WHERE id = ? AND status = 'PENDING' " +
                        "FOR UPDATE";

                try (PreparedStatement ps =
                        con.prepareStatement(findSql)) {

                    ps.setInt(1, requestId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        employeeId = rs.getInt("employee_id");
                        days = rs.getInt("leave_days");
                    }
                }

                if (decision.equals("APPROVED")) {
                    String balanceSql =
                            "SELECT total_days, used_days " +
                            "FROM leave_balance " +
                            "WHERE employee_id = ? FOR UPDATE";

                    try (PreparedStatement ps =
                            con.prepareStatement(balanceSql)) {

                        ps.setInt(1, employeeId);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()
                                    || days > rs.getInt("total_days")
                                              - rs.getInt("used_days")) {
                                con.rollback();
                                return false;
                            }
                        }
                    }

                    String updateBalance =
                            "UPDATE leave_balance " +
                            "SET used_days = used_days + ? " +
                            "WHERE employee_id = ?";

                    try (PreparedStatement ps =
                            con.prepareStatement(updateBalance)) {

                        ps.setInt(1, days);
                        ps.setInt(2, employeeId);
                        ps.executeUpdate();
                    }
                }

                String updateRequest =
                        "UPDATE leave_requests SET status = ? " +
                        "WHERE id = ? AND status = 'PENDING'";

                try (PreparedStatement ps =
                        con.prepareStatement(updateRequest)) {

                    ps.setString(1, decision);
                    ps.setInt(2, requestId);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                con.commit();
                return true;

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // View remaining leave balance
    public void showBalance(int employeeId)
            throws SQLException {

        String sql =
                "SELECT e.name, b.total_days, b.used_days " +
                "FROM employees e " +
                "JOIN leave_balance b ON e.id = b.employee_id " +
                "WHERE e.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total_days");
                    int used = rs.getInt("used_days");

                    System.out.println("Employee: "
                            + rs.getString("name"));
                    System.out.println("Total leave days: " + total);
                    System.out.println("Used leave days: " + used);
                    System.out.println("Remaining leave days: "
                            + (total - used));
                } else {
                    System.out.println("Employee not found.");
                }
            }
        }
    }
}
