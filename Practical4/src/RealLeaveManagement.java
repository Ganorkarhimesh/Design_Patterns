class RealLeaveManagementSystem implements LeaveManagementService {

    public void submitLeave(LeaveRequest request) {

        LeaveApprover hod = new HOD();
        LeaveApprover principal = new Principal();
        LeaveApprover director = new Director();

        hod.setNextApprover(principal);
        principal.setNextApprover(director);

        System.out.println("\nLeave Type : " + request.getLeaveType());

        hod.approveLeave(request);
        request.notifyApproval();
    }
}