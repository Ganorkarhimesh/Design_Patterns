public class InpatientTreatment extends PatientTreatment {

    @Override
    protected void performDiagnosticTest() {
        System.out.println("Inpatient: Detailed diagnostic tests performed.");
    }

    @Override
    protected void generatePrescription() {
        System.out.println("Inpatient: Prescription and treatment plan generated.");
    }
}
