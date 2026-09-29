public abstract class Department {

    protected Mediator mediator;
    protected String departmentName;

    public Department(Mediator mediator, String departmentName) {
        this.mediator = mediator;
        this.departmentName = departmentName;
    }

    public void sendMessage(String message) {
        System.out.println(departmentName + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    public void receiveMessage(String message) {
        System.out.println(departmentName + " received: " + message);
    }
}
