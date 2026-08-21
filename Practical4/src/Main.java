public class Main {

    public static void main(String[] args) {

        FacultyLeaveFactory permanent = new PermanentFacultyFactory();
        FacultyLeaveFactory contract = new ContractFacultyFactory();

        LeaveRequest l1 = permanent.createCasualLeave("Rem",2);
        LeaveRequest l2 = permanent.createMedicalLeave("Subaru",5);
        LeaveRequest l3 = permanent.createOnDutyLeave("Emilia",3);
        LeaveRequest l4 = contract.createCasualLeave("Cassie",1);
        LeaveRequest l5 = contract.createMedicalLeave("Sunny", 3);
        l1.setNotificationChannel(new email());
        l2.setNotificationChannel(new SMS());
        l3.setNotificationChannel(new Mobileapp());
        l4.setNotificationChannel(new email());

        LeaveManagementService service =
                new ProxyLeaveManagementSystem("admin","1234");

        service.submitLeave(l1);
        service.submitLeave(l2);
        service.submitLeave(l3);
        service.submitLeave(l4);


if (l5 != null) {
    l5.setNotificationChannel(new email());
    service.submitLeave(l5);
}
    }
}