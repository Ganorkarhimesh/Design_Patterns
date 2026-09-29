public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       MEDIATOR DESIGN PATTERN");
        System.out.println("========================================");

        HospitalCoordinator coordinator = new HospitalCoordinator();

        Reception reception = new Reception(coordinator);
        Doctor doctor = new Doctor(coordinator);
        Laboratory laboratory = new Laboratory(coordinator);
        Pharmacy pharmacy = new Pharmacy(coordinator);
        Billing billing = new Billing(coordinator);

        coordinator.registerDepartment(reception);
        coordinator.registerDepartment(doctor);
        coordinator.registerDepartment(laboratory);
        coordinator.registerDepartment(pharmacy);
        coordinator.registerDepartment(billing);

        System.out.println("\n--- Hospital Department Communication ---");

        reception.registerPatient("Rahul Sharma");

        doctor.consultPatient("Rahul Sharma");

        laboratory.performTest("Blood Test");

        pharmacy.provideMedicine("Paracetamol");

        billing.processBill("Rahul Sharma");

        System.out.println("\n========================================");
        System.out.println("      TEMPLATE METHOD DESIGN PATTERN");
        System.out.println("========================================");

        System.out.println("\n--- Outpatient Treatment ---");

        PatientTreatment outpatient = new OutpatientTreatment();
        outpatient.treatmentProcess();

        System.out.println("\n--- Inpatient Treatment ---");

        PatientTreatment inpatient = new InpatientTreatment();
        inpatient.treatmentProcess();
    }
}
