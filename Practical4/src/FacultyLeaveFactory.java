abstract class FacultyLeaveFactory {

    public abstract LeaveRequest createCasualLeave(String name, int days);

    public abstract LeaveRequest createMedicalLeave(String name, int days);

    public abstract LeaveRequest createOnDutyLeave(String name, int days);
}
