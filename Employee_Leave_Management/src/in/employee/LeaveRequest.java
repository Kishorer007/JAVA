
package in.employee;

public class LeaveRequest {

    private int id;
    private int employeeId;
    private int leaveDays;
    private String reason;
    private String status;

    public LeaveRequest(int id, int employeeId,
                        int leaveDays, String reason,
                        String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveDays = leaveDays;
        this.reason = reason;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Request ID: " + id
                + " | Employee ID: " + employeeId
                + " | Days: " + leaveDays
                + " | Reason: " + reason
                + " | Status: " + status;
    }
}
