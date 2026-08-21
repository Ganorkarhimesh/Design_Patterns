import java.util.ArrayList;
import java.util.List;

public class LeaveCoordinator {

    private List<Observer> observers = new ArrayList<>();
    private LeaveRequestData leaveRequest;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setLeaveRequest(LeaveRequestData leaveRequest) {
        this.leaveRequest = leaveRequest;
        notifyObservers();
    }

    public LeaveRequestData getLeaveRequest() {
        return leaveRequest;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(leaveRequest);
        }
    }

    public void updateLeaveStatus(String status) {
        if (leaveRequest != null) {
            leaveRequest.setStatus(status);

            System.out.println("\nLeave status changed to: " + status);
            notifyObservers();
        }
    }
}