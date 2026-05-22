public abstract class Payment {
    public abstract String getMethodName();
    public abstract String getPaymentDetail();
}

class CashPayment extends Payment {
    @Override
    public String getMethodName() { return "Cash"; }

    @Override
    public String getPaymentDetail() { return "N/A"; }
}

 class CardPayment extends Payment {
    private String cardNumber;

    public CardPayment(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public String getMethodName() { return "Card"; }

    @Override
    public String getPaymentDetail() { return cardNumber; }
}

 class MobilePayment extends Payment {
    private String transactionId;

    public MobilePayment(String transactionId) { this.transactionId = transactionId; }

    @Override
    public String getMethodName() { return "bKash/Nagad"; }

    @Override
    public String getPaymentDetail() { return transactionId; }
}