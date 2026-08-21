class email implements Notification {

    public void sendNotification(String facultyName, String leaveType) {
        System.out.println("Email Sent to " + facultyName +
                " : Your " + leaveType + " has been approved.");
    }
}