public class Laboratory extends Department {

    public Laboratory(Mediator mediator) {
        super(mediator, "Laboratory");
    }

    public void performTest(String testName) {
        sendMessage("Laboratory performed " + testName + ".");
    }
}
