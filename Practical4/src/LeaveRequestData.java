public class LeaveRequestData {

    private int leaveId;
    private String facultyName;
    private String leaveType;
    private int days;
    private String status;

    public LeaveRequestData(int leaveId, String facultyName,
                            String leaveType, int days, String status) {
        this.leaveId = leaveId;
        this.facultyName = facultyName;
        this.leaveType = leaveType;
        this.days = days;
        this.status = status;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public int getDays() {
        return days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}