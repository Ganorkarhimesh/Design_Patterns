class CasualLeave extends LeaveRequest {

    public CasualLeave(String facultyName, int days) {
        super(facultyName, days);
    }

    public String getLeaveType() {
        return "Casual Leave";
    }
}