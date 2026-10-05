public class BillingContext {

    private BillingStrategy strategy;

    public BillingContext(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public void generateBill(double amount) {
        double finalAmount = strategy.calculateBill(amount);

        System.out.println("Billing Category: " + strategy.getStrategyName());
        System.out.println("Original Amount: Rs. " + amount);
        System.out.println("Final Amount: Rs. " + finalAmount);
        System.out.println();
    }
}