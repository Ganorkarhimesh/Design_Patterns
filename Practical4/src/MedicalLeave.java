class MedicalLeave extends LeaveRequest {

    public MedicalLeave(String facultyName, int days) {
        super(facultyName, days);
    }

    public String getLeaveType() {
        return "Medical Leave";
    }
}