abstract class LeaveRequest {

    protected String facultyName;
    protected int days;

    protected Notification notificationChannel;

    public LeaveRequest(String facultyName, int days) {
        this.facultyName = facultyName;
        this.days = days;
    }

    public void setNotificationChannel(Notification notification) {
        this.notificationChannel = notification;
    }

    public void notifyApproval() {
        if(notificationChannel != null) {
            notificationChannel.sendNotification(facultyName, getLeaveType());
        }
    }

    public String getFacultyName() {
        return facultyName;
    }

    public int getDays() {
        return days;
    }

    public abstract String getLeaveType();

    public void display() {
        System.out.println("Faculty : " + facultyName);
        System.out.println("Leave Type : " + getLeaveType());
        System.out.println("Days : " + days);
        System.out.println();
    }
}