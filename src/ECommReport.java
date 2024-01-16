import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ECommReport extends Thread{
    private static final ArrayList<ECommOrder> orders = new ArrayList<>();
    private static Double totalRevenue = 0.0;
    private static Double totalProfit = 0.0;
    private final String reportName;
    private static final Map<String, Double> profitPerShirtSize = new HashMap<>();

    public ECommReport(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public void run() {
        if ("RevenueReport.csv".equals(reportName))
            generateRevenueReport(reportName, totalRevenue);
        else if ("ProfitReport.csv".equals(reportName))
            generateProfitReport(reportName, totalProfit);
        else if ("ProfitPerShirtSizeReport.csv".equals(reportName))
            generateProfitPerShirtSizeReport(reportName, profitPerShirtSize);
        else
            System.out.println("Not valid report name: " + reportName);
    }

    public static void runEComm() throws InterruptedException {
        ECommReport totalRevenueThread = new ECommReport("RevenueReport.csv");
        ECommReport totalProfitThread = new ECommReport("ProfitReport.csv");
        ECommReport profitPerShirtSizeThread = new ECommReport("ProfitPerShirtSizeReport.csv");

        totalRevenueThread.start();
        totalProfitThread.start();
        profitPerShirtSizeThread.start();

        totalRevenueThread.join();
        totalProfitThread.join();
        profitPerShirtSizeThread.join();
    }

    private void generateProfitReport(String fileName, double totalProfit) {
        generateReport(fileName, "Total profit: " + totalProfit);
    }

    private void generateRevenueReport(String fileName, double totalRevenue) {
        generateReport(fileName, "Total revenue: " + totalRevenue);
    }

    private void generateProfitPerShirtSizeReport(String fileName, Map<String, Double> profitPerShirtSize) {
        List<String> sizes = Arrays.asList("XS", "S", "M", "L", "XL", "2XL", "3XL");

        StringBuilder content = new StringBuilder("Total profit per shirt size:\n");
        sizes.stream()
                .filter(profitPerShirtSize::containsKey)
                .sorted(Comparator.comparingInt(sizes::indexOf))
                .forEach(size -> {
                    double value = profitPerShirtSize.get(size);
                    content.append(size).append(": ").append(value).append("\n");
                });

        generateReport(fileName, content.toString());
    }

    private void generateReport(String fileName, String content) {
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void readFromFile(String f) throws IOException {
        Scanner s = new Scanner(new File(f));
        s.nextLine();

        while (s.hasNextLine()){
            String line = s.nextLine();
            String[] parts = line.split(",");

            String shirt_size = parts[1].trim();
            Boolean with_design = Boolean.parseBoolean(parts[2].trim());
            Boolean with_hoodie = Boolean.parseBoolean(parts[3].trim());
            String payment = parts[4].trim();

            PaymentStrategy paymentStrategy = getPaymentStrategy(payment);
            profitPerShirtSize.putIfAbsent(shirt_size, 0.0);

            ECommOrder order = new ECommOrder(shirt_size, with_design, with_hoodie, paymentStrategy);
            orders.add(order);
        }
        for (ECommOrder order : orders)
            processCosts(order);
    }

    private static PaymentStrategy getPaymentStrategy(String payment) {
        PaymentStrategy paymentStrategy;
        if ("wallet".equalsIgnoreCase(payment))
            paymentStrategy = new WalletPayment();
        else if ("bankcard".equalsIgnoreCase(payment))
            paymentStrategy = new BankcardPayment();
        else if ("visa".equalsIgnoreCase(payment))
            paymentStrategy = new VisaPayment();
        else if ("mastercard".equalsIgnoreCase(payment))
                paymentStrategy = new MastercardPayment();
        else
            paymentStrategy = new OtherPayment();
        return paymentStrategy;
    }

    private static void processCosts(ECommOrder order) {
        double pricePerShirt = 40.0;
        double baseCostPerShirt = 14.0;
        double designCost = order.isWithDesign() ? 2.0 : 0.0;
        double hoodieCost = order.isWithHoodie() ? 3.0 : 0.0;
        double costPerShirt = baseCostPerShirt + designCost + hoodieCost;
        double transaction = order.calculateTransaction(pricePerShirt);
        double profitFromOrder = pricePerShirt - costPerShirt - transaction;

        totalRevenue += pricePerShirt;
        totalProfit += profitFromOrder;
        String shirtSize = order.getShirtSize();

        profitPerShirtSize.put(shirtSize, profitPerShirtSize.getOrDefault(shirtSize, 0.0) + profitFromOrder);
    }
}
