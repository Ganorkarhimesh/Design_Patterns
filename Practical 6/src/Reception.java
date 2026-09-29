public class Reception extends Department {

    public Reception(Mediator mediator) {
        super(mediator, "Reception");
    }

    public void registerPatient(String patientName) {
        sendMessage("Patient " + patientName + " registered.");
    }
}
