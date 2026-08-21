class OnDutyLeave extends LeaveRequest {

    public OnDutyLeave(String facultyName, int days) {
        super(facultyName, days);
    }

    public String getLeaveType() {
        return "On Duty Leave";
    }
}