public abstract class PatientTreatment {

    public final void treatmentProcess() {
        registerPatient();
        consultDoctor();
        performDiagnosticTest();
        generatePrescription();
        processBilling();
        dischargePatient();
    }

    protected void registerPatient() {
        System.out.println("Patient registered.");
    }

    protected void consultDoctor() {
        System.out.println("Patient consulted by doctor.");
    }

    protected abstract void performDiagnosticTest();

    protected abstract void generatePrescription();

    protected void processBilling() {
        System.out.println("Billing processed.");
    }

    protected void dischargePatient() {
        System.out.println("Patient discharged.");
    }
}
