import java.util.Scanner;
import java.util.ArrayList;

public class VehicleRentalSystem {
    static ArrayList<RentalTransaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        // Start the Swing GUI from the separate class
        VehicleRentalSystemGUI.launch();
    }

    // Original Console-compatible logic (preserved without changing code logic)
    private static void handleNewBooking(Scanner sc) {
        String name = "";
        while (name.trim().isEmpty()) {
            System.out.print("Customer Name: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) System.out.println("Error: Name cannot be blank.");
        }

        String mobile = "";
        while (mobile.trim().isEmpty()) {
            System.out.print("Contact Number: ");
            mobile = sc.nextLine();
            if (mobile.trim().isEmpty()) System.out.println("Error: Contact number cannot be blank.");
        }

        int vChoice = 0;
        while (vChoice < 1 || vChoice > 5) {
            System.out.println("\n1. Car  2. Bike  3. Truck  4. Microbus  5. Luxury");
            System.out.print("Choose Vehicle (1-5): ");
            try { vChoice = Integer.parseInt(sc.nextLine()); } catch (Exception e) { vChoice = 0; }
        }

        Vehicle selectedVehicle = switch (vChoice) {
            case 1 -> new Car("Standard Sedan");
            case 2 -> new Bike("MT - 15");
            case 3 -> new Truck("Heavy Duty Truck");
            case 4 -> new Microbus("Family Micro");
            case 5 -> new LuxuryCar("Premium SUV");
            default -> null;
        };
        int days = 0;
        while (days <= 0 || days > 150) {
            System.out.print("Enter Renting Days (Max 150): ");
            try {
                days = Integer.parseInt(sc.nextLine());
                if (days > 150) {
                    System.out.println("Policy Error: Maximum rental period is 150 days.");
                } else if (days <= 0) {
                    System.out.println("Error: Please enter a valid number of days.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a numeric value.");
                days = 0;
            }
        }

        boolean voucherValid = false;
        System.out.print("Have any special voucher? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter Voucher Code: ");
            String codeInput = sc.nextLine();

            for (RentalTransaction t : transactions) {
                if (t.getCustomerName().equalsIgnoreCase(name)) {
                    if (t.validateAndUseVoucher(codeInput)) {
                        voucherValid = true;
                        break;
                    }
                }
            }

            if (voucherValid) System.out.println("Success: 20% discount applied.");
            else System.out.println("Voucher invalid, used, or belongs to another customer.");
        }

        Payment payment = getPayment(sc);
        RentalTransaction transaction = new RentalTransaction(name, mobile, selectedVehicle, days, payment, voucherValid);
        transactions.add(transaction);
        transaction.printReceipt(false);
    }

    private static void handleRenewal(Scanner sc) {
        System.out.print("Customer Name: "); String name = sc.nextLine();
        System.out.print("Enter Vehicle Code: "); String code = sc.nextLine();

        for (RentalTransaction t : transactions) {
            if (t.getVehicleCode().equalsIgnoreCase(code) && t.getCustomerName().equalsIgnoreCase(name)) {
                System.out.print("Additional Days: ");
                int extra = Integer.parseInt(sc.nextLine());

                if (t.getTotalDuration() + extra > 150) {
                    System.out.println("Policy: Total limit (150 days) exceeded.");
                    return;
                }

                Payment payment = getPayment(sc);
                t.renew(extra, payment);
                t.printReceipt(true);
                return;
            }
        }
        System.out.println("Record not found!");
    }

    private static void handleReturn(Scanner sc) {
        System.out.print("Customer Name: "); String name = sc.nextLine();
        System.out.print("Enter Vehicle Code: "); String code = sc.nextLine();

        for (RentalTransaction t : transactions) {
            if (t.getVehicleCode().equalsIgnoreCase(code) && t.getCustomerName().equalsIgnoreCase(name)) {
                if (t.isReturned()) {
                    System.out.println("Vehicle already returned!");
                    return;
                }
                t.returnVehicle();
                t.printReceipt(false);
                System.out.println("Vehicle returned successfully!");
                return;
            }
        }
        System.out.println("Record not found!");
    }

    private static Payment getPayment(Scanner sc) {
        int choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.println("Payment: 1. Cash  2. Card  3. bKash/Nagad");
            try { choice = Integer.parseInt(sc.nextLine()); } catch (Exception e) { choice = 0; }
        }

        if (choice == 1) return new CashPayment();
        if (choice == 2) {
            System.out.print("Enter Card Number: ");
            return new CardPayment(sc.nextLine());
        }
        System.out.print("Enter Transaction ID: ");
        return new MobilePayment(sc.nextLine());
    }
}