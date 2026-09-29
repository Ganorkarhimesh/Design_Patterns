public class Doctor extends Department {

    public Doctor(Mediator mediator) {
        super(mediator, "Doctor");
    }

    public void consultPatient(String patientName) {
        sendMessage("Doctor consulted patient " + patientName + ".");
    }
}
