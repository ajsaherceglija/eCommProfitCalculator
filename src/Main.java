import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ECommReport.readFromFile("customer_orders.csv");
        ECommReport.runEComm();
    }
}