public class ECommOrder {
    private final String shirtSize;
    private final Boolean withDesign;
    private final Boolean withHoodie;
    private final PaymentStrategy paymentStrategy;

    public ECommOrder(String shirtSize, Boolean withDesign, Boolean withHoodie, PaymentStrategy paymentStrategy) {

        this.shirtSize = shirtSize;
        this.withDesign = withDesign;
        this.withHoodie = withHoodie;
        this.paymentStrategy = paymentStrategy;
    }

    public boolean isWithDesign() {
        return withDesign;
    }

    public Boolean isWithHoodie() {
        return withHoodie;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public double calculateTransaction(double amount) {
        return paymentStrategy.calculateTransaction(amount);
    }
}
