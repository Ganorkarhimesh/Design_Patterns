public class Billing extends Department {

    public Billing(Mediator mediator) {
        super(mediator, "Billing");
    }

    public void processBill(String patientName) {
        sendMessage("Billing processed for patient " + patientName + ".");
    }
}
