public class HRDepartment implements Observer {

    @Override
    public void update(LeaveRequestData request) {
        System.out.println(
            "HR Department notified: Leave ID " +
            request.getLeaveId() +
            " status is " +
            request.getStatus()
        );
    }
}