public class Main {

    public static void main(String[] args) {

        System.out.println("===== STRATEGY PATTERN =====");
        System.out.println();

        double amount = 10000;

        Billing billing = new Billing(new SelfPayStrategy());
        billing.generateBill(amount);

        billing.setStrategy(new InsuranceStrategy());
        billing.generateBill(amount);

        billing.setStrategy(new CorporateStrategy());
        billing.generateBill(amount);

        billing.setStrategy(new SeniorCitizenStrategy());
        billing.generateBill(amount);


        System.out.println("===== MEMENTO PATTERN =====");
        System.out.println();

        PatientTreatmentRecord patient =
                new PatientTreatmentRecord(
                        "Rahul",
                        "Initial consultation completed"
                );

        TreatmentHistory history = new TreatmentHistory();

        patient.displayRecord();

        history.save(patient.save());

        patient.updateNotes("Blood test recommended");
        patient.displayRecord();

        history.save(patient.save());

        patient.updateNotes("Medicine prescribed");
        patient.displayRecord();

        System.out.println("Undo performed:");

        TreatmentMemento previousState = history.undo();

        if (previousState != null) {
            patient.restore(previousState);
        }

        patient.displayRecord();

        System.out.println("Undo performed:");

        previousState = history.undo();

        if (previousState != null) {
            patient.restore(previousState);
        }

        patient.displayRecord();
    }
}
