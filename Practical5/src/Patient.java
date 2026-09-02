public class Patient implements Cloneable {

    private String patientId;
    private String name;
    private int age;

    private String insuranceDetails;
    private String roomType;
    private String bloodGroup;
    private String allergies;
    private String emergencyContact;
    private String medicalHistory;

    public Patient(String patientId, String name, int age) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
    }

    public void setInsuranceDetails(String insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Patient clone() {
        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void display() {
        System.out.println("Patient ID        : " + patientId);
        System.out.println("Name              : " + name);
        System.out.println("Age               : " + age);
        System.out.println("Insurance Details : " + insuranceDetails);
        System.out.println("Room Type         : " + roomType);
        System.out.println("Blood Group       : " + bloodGroup);
        System.out.println("Allergies         : " + allergies);
        System.out.println("Emergency Contact : " + emergencyContact);
        System.out.println("Medical History   : " + medicalHistory);
    }
}