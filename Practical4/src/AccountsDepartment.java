public class AccountsDepartment implements Observer {

    @Override
    public void update(LeaveRequestData request) {
        System.out.println(
            "Accounts Department notified: Leave ID " +
            request.getLeaveId() +
            " status is " +
            request.getStatus()
        );
    }
}