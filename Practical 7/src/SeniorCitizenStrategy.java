public class SeniorCitizenStrategy implements BillingStrategy {

    public double calculateBill(double amount) {
        return amount * 0.60;
    }

    public String getStrategyName() {
        return "Senior Citizen";
    }
}