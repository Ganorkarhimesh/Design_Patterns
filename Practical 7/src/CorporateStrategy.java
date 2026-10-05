public class CorporateStrategy implements BillingStrategy {

    public double calculateBill(double amount) {
        return amount * 0.70;
    }

    public String getStrategyName() {
        return "Corporate";
    }
}