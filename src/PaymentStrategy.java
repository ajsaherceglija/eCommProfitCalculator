public interface PaymentStrategy {
    double calculateTransaction(double amount);
}

class WalletPayment implements PaymentStrategy {
    @Override
    public double calculateTransaction(double amount){
        return 0.0;
    }
}

class BankcardPayment implements PaymentStrategy {
    @Override
    public double calculateTransaction(double amount){
        return amount * 0.05;
    }
}

class VisaPayment implements PaymentStrategy {
    @Override
    public double calculateTransaction(double amount){
        return amount * 0.02;
    }
}

class MastercardPayment implements PaymentStrategy {
    @Override
    public double calculateTransaction(double amount){
        return amount * 0.03;
    }
}

class OtherPayment implements PaymentStrategy {
    @Override
    public double calculateTransaction(double amount){
        return amount * 0.1;
    }
}