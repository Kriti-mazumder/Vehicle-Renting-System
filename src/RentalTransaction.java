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
    private boolean isReturned = false;

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

    public boolean validateAndUseVoucher(String inputCode) {
        if (!loyaltyCode.equals("NONE") && inputCode.equalsIgnoreCase(loyaltyCode)) {
            if (!this.voucherRedeemed) {
                this.voucherRedeemed = true;
                return true;
            }
        }
        return false;
    }

    public double calculateCost(int days) {
        double cost = vehicle.calculateRent(days);
        if (hasAppliedVoucher) cost *= 0.80;
        else if (days >= 100) cost *= 0.85;
        else if (days >= 60) cost *= 0.90;
        return cost;
    }

    public String getReceiptDetails(boolean isRenewal) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(isRenewal ? "RENEWAL" : "RENTAL").append(" RECEIPT ---\n");
        if (isRenewal) {
            sb.append("Original Date:    ").append(originalDate).append("\n");
        } else {
            sb.append("Renting Date:     ").append(originalDate).append("\n");
        }
        sb.append("Customer Name:    ").append(customerName).append("\n");
        sb.append("Contact Number:   ").append(mobileNumber).append("\n");
        sb.append("Vehicle Code:     ").append(vehicle.getVehicleCode()).append("\n");
        sb.append("Vehicle Model:    ").append(vehicle.getModel()).append("\n");
        if (isRenewal) {
            sb.append("Past Rented:    ").append(totalDuration - currentTermDays).append(" Days\n");
            sb.append("New Extension:  ").append(currentTermDays).append(" Days\n");
            sb.append("Total Period:   ").append(totalDuration).append(" Days\n");
        } else {
            sb.append("Renting Days:   ").append(currentTermDays).append("\n");
        }
        sb.append("Payment Info:   ").append(payment.getPaymentDetail()).append(" (").append(payment.getMethodName()).append(")\n");
        double finalCost = calculateCost(currentTermDays);
        sb.append("TOTAL AMOUNT:   ").append(finalCost).append(" Taka\n");
        if (hasAppliedVoucher) {
            sb.append("[DISCOUNT]: 20% Loyalty Voucher applied!\n");
        } else if (currentTermDays >= 100) {
            sb.append("[DISCOUNT]: 15% Long-term discount applied!\n");
        } else if (currentTermDays >= 60) {
            sb.append("[DISCOUNT]: 10% Standard discount applied!\n");
        }
        if (!loyaltyCode.equals("NONE")) {
            sb.append("Loyalty Voucher Code: ").append(loyaltyCode).append(voucherRedeemed ? " (USED)" : " (AVAILABLE)").append("\n");
        }
        sb.append("Status:         ").append(isReturned ? "RETURNED" : "RENTED/ACTIVE").append("\n");
        sb.append("------------------------------------\n");
        return sb.toString();
    }

    public void printReceipt(boolean isRenewal) {
        System.out.print("\n" + getReceiptDetails(isRenewal));
    }

    public String getCustomerName() { return customerName; }
    public String getVehicleCode() { return vehicle.getVehicleCode(); }
    public int getTotalDuration() { return totalDuration; }
    public boolean isReturned() { return isReturned; }
    public void returnVehicle() { this.isReturned = true; }
    public String getMobileNumber() { return mobileNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public int getCurrentTermDays() { return currentTermDays; }
    public Payment getPayment() { return payment; }
    public boolean isHasAppliedVoucher() { return hasAppliedVoucher; }
    public String getLoyaltyCode() { return loyaltyCode; }
    public boolean isVoucherRedeemed() { return voucherRedeemed; }
}