public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       BUILDER DESIGN PATTERN");
        System.out.println("========================================");

        Patient patient1 = new PatientBuilder(
                "P101", "Rahul Sharma", 35)
                .setBloodGroup("B+")
                .setAllergies("None")
                .setEmergencyContact("9876543210")
                .setMedicalHistory("Diabetes")
                .build();

        System.out.println("\n--- Outpatient ---");
        patient1.display();

        Patient patient2 = new PatientBuilder(
                "P102", "Priya Patil", 42)
                .setInsuranceDetails("Health Secure Insurance")
                .setRoomType("Private Room")
                .setBloodGroup("O+")
                .setAllergies("Penicillin")
                .setEmergencyContact("9876501234")
                .setMedicalHistory("Hypertension")
                .build();

        System.out.println("\n--- Inpatient ---");
        patient2.display();

        Patient patient3 = new PatientBuilder(
                "P103", "Amit Verma", 28)
                .setRoomType("Emergency Ward")
                .setBloodGroup("A+")
                .setAllergies("Dust")
                .setEmergencyContact("9988776655")
                .setMedicalHistory("Fracture")
                .build();

        System.out.println("\n--- Emergency Patient ---");
        patient3.display();

        System.out.println("\n========================================");
        System.out.println("      PROTOTYPE DESIGN PATTERN");
        System.out.println("========================================");

        Patient clonedPatient = patient2.clone();

        clonedPatient.setRoomType("Semi-Private Room");
        clonedPatient.setMedicalHistory("Hypertension and Diabetes");

        System.out.println("\n--- Original Patient ---");
        patient2.display();

        System.out.println("\n--- Cloned Patient ---");
        clonedPatient.display();

        System.out.println("\nPatient record cloned successfully.");
    }
}