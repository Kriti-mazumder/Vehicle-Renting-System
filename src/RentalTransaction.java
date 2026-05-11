import java.time.LocalDate;

public class RentalTransaction {
    private String customerName;
    private String mobileNumber;
    private Vehicle vehicle;
    private LocalDate originalDate;
    private int totalDuration;
    private int currentTermDays;
    private Payment payment;
    private boolean hasAppliedVoucher = false;
    private String loyaltyCode = "NONE";
    private boolean voucherRedeemed = false;

    public RentalTransaction(String name, String mobile, Vehicle v, int days, Payment payment, boolean isVoucherUsed) {
        this.customerName = name;
        this.mobileNumber = mobile;
        this.vehicle = v;
        this.currentTermDays = days;
        this.totalDuration = days;
        this.originalDate = LocalDate.now();
        this.payment = payment;
        this.hasAppliedVoucher = isVoucherUsed;
        checkLoyalty();
    }

    public void renew(int days, Payment payment) {
        this.currentTermDays = days;
        this.totalDuration += days;
        this.payment = payment;
        checkLoyalty();
    }

    private void checkLoyalty() {
        if (this.totalDuration > 120) {
            this.loyaltyCode = "LOYAL-" + vehicle.getVehicleCode();
        }
    }

    // Method to handle the "Universal" and "Single-use" logic
    public boolean validateAndUseVoucher(String inputCode) {
        if (!loyaltyCode.equals("NONE") && inputCode.equalsIgnoreCase(loyaltyCode)) {
            if (!this.voucherRedeemed) {
                this.voucherRedeemed = true; // Lock it so it can't be used again
                return true;
            }
        }
        return false;
    }

    public double calculateCost(int days) {
        double cost = vehicle.calculateRent(days);
        if (hasAppliedVoucher) cost *= 0.80; // 20% discount
        else if (days >= 100) cost *= 0.85;
        else if (days >= 60) cost *= 0.90;
        return cost;
    }

    public void printReceipt(boolean isRenewal) {
        System.out.println("\n--- " + (isRenewal ? "RENEWAL" : "RENTAL") + " RECEIPT ---");

        // Restoration of the Date logic
        if (isRenewal) {
            System.out.println("Original Date:    " + originalDate);
        } else {
            System.out.println("Renting Date:     " + originalDate);
        }

        System.out.println("Customer Name:    " + customerName);
        System.out.println("Contact Number:   " + mobileNumber);
        System.out.println("Vehicle Code:     " + vehicle.getVehicleCode());
        System.out.println("Vehicle Model:    " + vehicle.getModel());

        if (isRenewal) {
            System.out.println("Past Rented:    " + (totalDuration - currentTermDays) + " Days");
            System.out.println("New Extension:  " + currentTermDays + " Days");
            System.out.println("Total Period:   " + totalDuration + " Days");
        } else {
            System.out.println("Renting Days:   " + currentTermDays);
        }

        System.out.println("Payment Info:   " + payment.getPaymentDetail() + " (" + payment.getMethodName() + ")");

        double finalCost = calculateCost(currentTermDays);
        System.out.println("TOTAL AMOUNT:   " + finalCost + " Taka");

        // Discount Notes
        if (hasAppliedVoucher) {
            System.out.println("[DISCOUNT]: 20% Loyalty Voucher applied!");
        } else if (currentTermDays >= 100) {
            System.out.println("[DISCOUNT]: 15% Long-term discount applied!");
        } else if (currentTermDays >= 60) {
            System.out.println("[DISCOUNT]: 10% Standard discount applied!");
        }

        if (!loyaltyCode.equals("NONE")) {
            System.out.println("Loyalty Voucher Code: " + loyaltyCode + (voucherRedeemed ? " (USED)" : " (AVAILABLE)"));
        }
        System.out.println("------------------------------------\n");
    }

    public String getCustomerName() { return customerName; }
    public String getVehicleCode() { return vehicle.getVehicleCode(); }
    public int getTotalDuration() { return totalDuration; }
}