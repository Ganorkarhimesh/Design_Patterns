class PermanentFacultyFactory extends FacultyLeaveFactory {


    public LeaveRequest createCasualLeave(String name, int days) {
        return new CasualLeave(name, days);
    }

    public LeaveRequest createMedicalLeave(String name, int days) {
        return new MedicalLeave(name, days);
    }

    public LeaveRequest createOnDutyLeave(String name, int days) {
        return new OnDutyLeave(name, days);
    }
}