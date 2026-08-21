package org.xerial.sqlitejdbc;

class SMS implements Notification {

    public void sendNotification(String facultyName, String leaveType) {
        System.out.println("SMS Sent to " + facultyName +
                " : Your " + leaveType + " has been approved.");
    }
}