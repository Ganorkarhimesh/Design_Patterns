class Mobileapp implements Notification {

 
    public void sendNotification(String facultyName, String leaveType) {
        System.out.println("Mobile App Notification to " + facultyName +
                " : Your " + leaveType + " has been approved.");
    }
}