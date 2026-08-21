class ContractFacultyFactory extends FacultyLeaveFactory {

  
    public LeaveRequest createCasualLeave(String name, int days) {
        return new CasualLeave(name, days);
    }

  
    public LeaveRequest createMedicalLeave(String name, int days) {
        System.out.println("\nMedical Leave is NOT allowed for Contract Faculty.");
        return null;
    }

    
    public LeaveRequest createOnDutyLeave(String name, int days) {
        return new OnDutyLeave(name, days);
    }
}