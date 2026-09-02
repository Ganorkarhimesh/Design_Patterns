public class PatientBuilder {

    private Patient patient;

    public PatientBuilder(String patientId, String name, int age) {
        patient = new Patient(patientId, name, age);
    }

    public PatientBuilder setInsuranceDetails(String insuranceDetails) {
        patient.setInsuranceDetails(insuranceDetails);
        return this;
    }

    public PatientBuilder setRoomType(String roomType) {
        patient.setRoomType(roomType);
        return this;
    }

    public PatientBuilder setBloodGroup(String bloodGroup) {
        patient.setBloodGroup(bloodGroup);
        return this;
    }

    public PatientBuilder setAllergies(String allergies) {
        patient.setAllergies(allergies);
        return this;
    }

    public PatientBuilder setEmergencyContact(String emergencyContact) {
        patient.setEmergencyContact(emergencyContact);
        return this;
    }

    public PatientBuilder setMedicalHistory(String medicalHistory) {
        patient.setMedicalHistory(medicalHistory);
        return this;
    }

    public Patient build() {
        return patient;
    }
}