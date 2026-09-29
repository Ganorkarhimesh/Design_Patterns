public class OutpatientTreatment extends PatientTreatment {

    @Override
    protected void performDiagnosticTest() {
        System.out.println("Outpatient: Basic diagnostic test performed.");
    }

    @Override
    protected void generatePrescription() {
        System.out.println("Outpatient: Prescription generated.");
    }
}
