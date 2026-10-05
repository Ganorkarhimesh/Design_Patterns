public class InsuranceStrategy implements BillingStrategy {

    public double calculateBill(double amount) {
        return amount * 0.80;
    }

    public String getStrategyName() {
        return "Insurance";
    }
}