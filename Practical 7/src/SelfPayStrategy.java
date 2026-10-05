public class SelfPayStrategy implements BillingStrategy {

    public double calculateBill(double amount) {
        return amount;
    }

    public String getStrategyName() {
        return "Self-Pay";
    }
}