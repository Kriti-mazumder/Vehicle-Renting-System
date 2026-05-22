import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class VehicleRentalSystemGUI {

    // Styling constants
    private static final Color COLOR_BG = new Color(30, 32, 34);
    private static final Color COLOR_CARD_BG = new Color(43, 45, 66);
    private static final Color COLOR_INPUT_BG = new Color(30, 30, 40);
    private static final Color COLOR_PRIMARY = new Color(137, 180, 250);
    private static final Color COLOR_TEXT_MAIN = new Color(248, 249, 250);
    private static final Color COLOR_TEXT_MUTED = new Color(166, 173, 200);
    private static final Color COLOR_ACCENT_RED = new Color(243, 139, 168);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBHEADER = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 13);

    private DefaultTableModel tableModel;
    private JTable transactionTable;

    public static void launch() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VehicleRentalSystemGUI().createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Vehicle Rental Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);
        
        // Main container panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_BG);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_CARD_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("VEHICLE RENTAL MANAGEMENT SYSTEM");
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(COLOR_PRIMARY);
        
        JLabel subtitleLabel = new JLabel("Manage your bookings, renewals, and returns professionally");
        subtitleLabel.setFont(FONT_SUBHEADER);
        subtitleLabel.setForeground(COLOR_TEXT_MUTED);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(COLOR_BG);
        tabbedPane.setForeground(COLOR_TEXT_MAIN);
        tabbedPane.setFont(FONT_LABEL);
        
        // Create Tabs
        tabbedPane.addTab("New Rental Booking", createNewBookingPanel());
        tabbedPane.addTab("Renew Rental", createRenewPanel());
        tabbedPane.addTab("Return Vehicle", createReturnPanel());
        tabbedPane.addTab("View Transactions", createViewTransactionsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createNewBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // Customer Name
        addGridComponent(formPanel, createStyledLabel("Customer Name:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfName = new JTextField(15);
        styleTextField(tfName);
        addGridComponent(formPanel, tfName, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Contact Number
        addGridComponent(formPanel, createStyledLabel("Contact Number:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfContact = new JTextField(15);
        styleTextField(tfContact);
        addGridComponent(formPanel, tfContact, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Choose Vehicle
        addGridComponent(formPanel, createStyledLabel("Vehicle Type:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JComboBox<String> cbVehicle = new JComboBox<>(new String[]{
            "Car - Standard Sedan (500 Taka/day)",
            "Bike - MT - 15 (150 Taka/day)",
            "Truck - Heavy Duty Truck (1000 Taka/day)",
            "Microbus - Family Micro (720 Taka/day)",
            "Luxury - Premium SUV (1500 Taka/day)"
        });
        styleComboBox(cbVehicle);
        addGridComponent(formPanel, cbVehicle, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Renting Days
        addGridComponent(formPanel, createStyledLabel("Renting Days (Max 150):"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfDays = new JTextField(15);
        styleTextField(tfDays);
        addGridComponent(formPanel, tfDays, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Voucher checkbox & input
        JCheckBox chkVoucher = new JCheckBox("Have voucher?");
        styleCheckBox(chkVoucher);
        JTextField tfVoucher = new JTextField(10);
        styleTextField(tfVoucher);
        tfVoucher.setEnabled(false);
        chkVoucher.addActionListener(e -> tfVoucher.setEnabled(chkVoucher.isSelected()));
        
        addGridComponent(formPanel, chkVoucher, gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        addGridComponent(formPanel, tfVoucher, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Payment Method
        addGridComponent(formPanel, createStyledLabel("Payment Method:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JComboBox<String> cbPayment = new JComboBox<>(new String[]{"Cash", "Card", "bKash/Nagad"});
        styleComboBox(cbPayment);
        addGridComponent(formPanel, cbPayment, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Payment Detail (Card No / Trans ID)
        JLabel lblPayDetail = createStyledLabel("Card No / Trans ID:");
        addGridComponent(formPanel, lblPayDetail, gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfPayDetail = new JTextField(15);
        styleTextField(tfPayDetail);
        tfPayDetail.setEnabled(false);
        addGridComponent(formPanel, tfPayDetail, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        cbPayment.addActionListener(e -> {
            boolean needDetails = cbPayment.getSelectedIndex() > 0;
            tfPayDetail.setEnabled(needDetails);
            if (!needDetails) tfPayDetail.setText("");
        });
        
        // Book Button
        JButton btnBook = createStyledButton("Confirm Booking", COLOR_PRIMARY, Color.BLACK);
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        addGridComponent(formPanel, btnBook, gbc, 0, row++, 2, 1, GridBagConstraints.HORIZONTAL);
        
        // Right Side: Receipt Output
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBackground(COLOR_CARD_BG);
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblReceiptTitle = new JLabel("Rental Receipt Details");
        lblReceiptTitle.setFont(FONT_LABEL);
        lblReceiptTitle.setForeground(COLOR_PRIMARY);
        receiptPanel.add(lblReceiptTitle, BorderLayout.NORTH);
        
        JTextArea taReceipt = new JTextArea();
        taReceipt.setEditable(false);
        taReceipt.setBackground(COLOR_INPUT_BG);
        taReceipt.setForeground(COLOR_TEXT_MAIN);
        taReceipt.setFont(FONT_MONO);
        taReceipt.setText("\n  Receipt will be generated here after booking...");
        receiptPanel.add(new JScrollPane(taReceipt), BorderLayout.CENTER);
        
        panel.add(formPanel);
        panel.add(receiptPanel);
        
        btnBook.addActionListener(e -> {
            String name = tfName.getText().trim();
            String contact = tfContact.getText().trim();
            String daysStr = tfDays.getText().trim();
            
            if (name.isEmpty() || contact.isEmpty() || daysStr.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Name, Contact Number, and Renting Days are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int days = 0;
            try {
                days = Integer.parseInt(daysStr);
                if (days <= 0 || days > 150) {
                    JOptionPane.showMessageDialog(panel, "Error: Renting days must be between 1 and 150.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Error: Renting days must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int vChoice = cbVehicle.getSelectedIndex() + 1;
            Vehicle selectedVehicle = switch (vChoice) {
                case 1 -> new Car("Standard Sedan");
                case 2 -> new Bike("MT - 15");
                case 3 -> new Truck("Heavy Duty Truck");
                case 4 -> new Microbus("Family Micro");
                case 5 -> new LuxuryCar("Premium SUV");
                default -> null;
            };
            
            boolean voucherValid = false;
            if (chkVoucher.isSelected()) {
                String voucherCode = tfVoucher.getText().trim();
                for (RentalTransaction t : VehicleRentalSystem.transactions) {
                    if (t.getCustomerName().equalsIgnoreCase(name)) {
                        if (t.validateAndUseVoucher(voucherCode)) {
                            voucherValid = true;
                            break;
                        }
                    }
                }
                if (!voucherValid) {
                    JOptionPane.showMessageDialog(panel, "Voucher invalid, already used, or belongs to another customer.", "Voucher Info", JOptionPane.WARNING_MESSAGE);
                }
            }
            
            int payChoice = cbPayment.getSelectedIndex();
            Payment payment;
            String payDetailText = tfPayDetail.getText().trim();
            if (payChoice == 0) {
                payment = new CashPayment();
            } else if (payChoice == 1) {
                if (payDetailText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Error: Card number is required for Card payment.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                payment = new CardPayment(payDetailText);
            } else {
                if (payDetailText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Error: Transaction ID is required for mobile payment.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                payment = new MobilePayment(payDetailText);
            }
            
            RentalTransaction transaction = new RentalTransaction(name, contact, selectedVehicle, days, payment, voucherValid);
            VehicleRentalSystem.transactions.add(transaction);
            
            taReceipt.setText(transaction.getReceiptDetails(false));
            JOptionPane.showMessageDialog(panel, "Rental booked successfully!\nVehicle Code: " + selectedVehicle.getVehicleCode(), "Success", JOptionPane.INFORMATION_MESSAGE);
            
            tfName.setText("");
            tfContact.setText("");
            tfDays.setText("");
            chkVoucher.setSelected(false);
            tfVoucher.setText("");
            tfVoucher.setEnabled(false);
            cbPayment.setSelectedIndex(0);
            tfPayDetail.setText("");
            tfPayDetail.setEnabled(false);
            
            refreshTransactionTable();
        });
        
        return panel;
    }

    private JPanel createRenewPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // Customer Name
        addGridComponent(formPanel, createStyledLabel("Customer Name:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfName = new JTextField(15);
        styleTextField(tfName);
        addGridComponent(formPanel, tfName, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Vehicle Code
        addGridComponent(formPanel, createStyledLabel("Vehicle Code:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfVehicleCode = new JTextField(15);
        styleTextField(tfVehicleCode);
        addGridComponent(formPanel, tfVehicleCode, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Additional Days
        addGridComponent(formPanel, createStyledLabel("Additional Days:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfExtraDays = new JTextField(15);
        styleTextField(tfExtraDays);
        addGridComponent(formPanel, tfExtraDays, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Payment Method
        addGridComponent(formPanel, createStyledLabel("Payment Method:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JComboBox<String> cbPayment = new JComboBox<>(new String[]{"Cash", "Card", "bKash/Nagad"});
        styleComboBox(cbPayment);
        addGridComponent(formPanel, cbPayment, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Payment Detail (Card No / Trans ID)
        JLabel lblPayDetail = createStyledLabel("Card No / Trans ID:");
        addGridComponent(formPanel, lblPayDetail, gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfPayDetail = new JTextField(15);
        styleTextField(tfPayDetail);
        tfPayDetail.setEnabled(false);
        addGridComponent(formPanel, tfPayDetail, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        cbPayment.addActionListener(e -> {
            boolean needDetails = cbPayment.getSelectedIndex() > 0;
            tfPayDetail.setEnabled(needDetails);
            if (!needDetails) tfPayDetail.setText("");
        });
        
        // Renew Button
        JButton btnRenew = createStyledButton("Confirm Renewal", COLOR_PRIMARY, Color.BLACK);
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        addGridComponent(formPanel, btnRenew, gbc, 0, row++, 2, 1, GridBagConstraints.HORIZONTAL);
        
        // Right Side: Receipt Output
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBackground(COLOR_CARD_BG);
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblReceiptTitle = new JLabel("Renewal Receipt Details");
        lblReceiptTitle.setFont(FONT_LABEL);
        lblReceiptTitle.setForeground(COLOR_PRIMARY);
        receiptPanel.add(lblReceiptTitle, BorderLayout.NORTH);
        
        JTextArea taReceipt = new JTextArea();
        taReceipt.setEditable(false);
        taReceipt.setBackground(COLOR_INPUT_BG);
        taReceipt.setForeground(COLOR_TEXT_MAIN);
        taReceipt.setFont(FONT_MONO);
        taReceipt.setText("\n  Receipt will be generated here after renewal...");
        receiptPanel.add(new JScrollPane(taReceipt), BorderLayout.CENTER);
        
        panel.add(formPanel);
        panel.add(receiptPanel);
        
        btnRenew.addActionListener(e -> {
            String name = tfName.getText().trim();
            String code = tfVehicleCode.getText().trim();
            String extraStr = tfExtraDays.getText().trim();
            
            if (name.isEmpty() || code.isEmpty() || extraStr.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Name, Vehicle Code, and Additional Days are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int extra = 0;
            try {
                extra = Integer.parseInt(extraStr);
                if (extra <= 0) {
                    JOptionPane.showMessageDialog(panel, "Error: Additional days must be greater than 0.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Error: Additional days must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            RentalTransaction targetTransaction = null;
            for (RentalTransaction t : VehicleRentalSystem.transactions) {
                if (t.getVehicleCode().equalsIgnoreCase(code) && t.getCustomerName().equalsIgnoreCase(name)) {
                    targetTransaction = t;
                    break;
                }
            }
            
            if (targetTransaction == null) {
                JOptionPane.showMessageDialog(panel, "Error: Rental record not found matching Name and Vehicle Code.", "Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (targetTransaction.isReturned()) {
                JOptionPane.showMessageDialog(panel, "Error: This vehicle has already been returned.", "Operation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (targetTransaction.getTotalDuration() + extra > 150) {
                JOptionPane.showMessageDialog(panel, "Policy Limit Exceeded: Total duration cannot exceed 150 days.\nCurrent total: " + targetTransaction.getTotalDuration() + " days.", "Policy Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int payChoice = cbPayment.getSelectedIndex();
            Payment payment;
            String payDetailText = tfPayDetail.getText().trim();
            if (payChoice == 0) {
                payment = new CashPayment();
            } else if (payChoice == 1) {
                if (payDetailText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Error: Card number is required for Card payment.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                payment = new CardPayment(payDetailText);
            } else {
                if (payDetailText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Error: Transaction ID is required for mobile payment.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                payment = new MobilePayment(payDetailText);
            }
            
            targetTransaction.renew(extra, payment);
            
            taReceipt.setText(targetTransaction.getReceiptDetails(true));
            JOptionPane.showMessageDialog(panel, "Rental renewed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            tfName.setText("");
            tfVehicleCode.setText("");
            tfExtraDays.setText("");
            cbPayment.setSelectedIndex(0);
            tfPayDetail.setText("");
            tfPayDetail.setEnabled(false);
            
            refreshTransactionTable();
        });
        
        return panel;
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // Customer Name
        addGridComponent(formPanel, createStyledLabel("Customer Name:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfName = new JTextField(15);
        styleTextField(tfName);
        addGridComponent(formPanel, tfName, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Vehicle Code
        addGridComponent(formPanel, createStyledLabel("Vehicle Code:"), gbc, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        JTextField tfVehicleCode = new JTextField(15);
        styleTextField(tfVehicleCode);
        addGridComponent(formPanel, tfVehicleCode, gbc, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL);
        
        // Return Button
        JButton btnReturn = createStyledButton("Process Vehicle Return", COLOR_ACCENT_RED, Color.BLACK);
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        addGridComponent(formPanel, btnReturn, gbc, 0, row++, 2, 1, GridBagConstraints.HORIZONTAL);
        
        // Right Side: Return Output
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBackground(COLOR_CARD_BG);
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblReceiptTitle = new JLabel("Return Status & Details");
        lblReceiptTitle.setFont(FONT_LABEL);
        lblReceiptTitle.setForeground(COLOR_ACCENT_RED);
        receiptPanel.add(lblReceiptTitle, BorderLayout.NORTH);
        
        JTextArea taReceipt = new JTextArea();
        taReceipt.setEditable(false);
        taReceipt.setBackground(COLOR_INPUT_BG);
        taReceipt.setForeground(COLOR_TEXT_MAIN);
        taReceipt.setFont(FONT_MONO);
        taReceipt.setText("\n  Details of the returned rental will be displayed here...");
        receiptPanel.add(new JScrollPane(taReceipt), BorderLayout.CENTER);
        
        panel.add(formPanel);
        panel.add(receiptPanel);
        
        btnReturn.addActionListener(e -> {
            String name = tfName.getText().trim();
            String code = tfVehicleCode.getText().trim();
            
            if (name.isEmpty() || code.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Both Customer Name and Vehicle Code are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            RentalTransaction match = null;
            for (RentalTransaction t : VehicleRentalSystem.transactions) {
                if (t.getVehicleCode().equalsIgnoreCase(code) && t.getCustomerName().equalsIgnoreCase(name)) {
                    match = t;
                    break;
                }
            }
            
            if (match == null) {
                JOptionPane.showMessageDialog(panel, "Error: Rental transaction not found matching the given Customer Name and Vehicle Code.", "Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (match.isReturned()) {
                JOptionPane.showMessageDialog(panel, "Error: This vehicle has already been marked as returned.", "Already Returned", JOptionPane.WARNING_MESSAGE);
                taReceipt.setText(match.getReceiptDetails(false));
                return;
            }
            
            match.returnVehicle();
            
            taReceipt.setText(match.getReceiptDetails(false));
            JOptionPane.showMessageDialog(panel, "Vehicle returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            tfName.setText("");
            tfVehicleCode.setText("");
            
            refreshTransactionTable();
        });
        
        return panel;
    }

    private JPanel createViewTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(COLOR_CARD_BG);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        String[] columnNames = {"Customer Name", "Contact No", "Vehicle Code", "Vehicle Model", "Duration", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionTable = new JTable(tableModel);
        transactionTable.setBackground(COLOR_INPUT_BG);
        transactionTable.setForeground(COLOR_TEXT_MAIN);
        transactionTable.setGridColor(COLOR_BG);
        transactionTable.setFont(FONT_INPUT);
        transactionTable.setRowHeight(24);
        transactionTable.getTableHeader().setBackground(COLOR_CARD_BG);
        transactionTable.getTableHeader().setForeground(COLOR_PRIMARY);
        transactionTable.getTableHeader().setFont(FONT_LABEL);
        
        JScrollPane tableScroll = new JScrollPane(transactionTable);
        tableScroll.getViewport().setBackground(COLOR_INPUT_BG);
        leftPanel.add(tableScroll, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(COLOR_CARD_BG);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        rightPanel.setPreferredSize(new Dimension(350, 0));
        
        JLabel lblDetailTitle = new JLabel("Transaction Details");
        lblDetailTitle.setFont(FONT_LABEL);
        lblDetailTitle.setForeground(COLOR_PRIMARY);
        rightPanel.add(lblDetailTitle, BorderLayout.NORTH);
        
        JTextArea taDetail = new JTextArea();
        taDetail.setEditable(false);
        taDetail.setBackground(COLOR_INPUT_BG);
        taDetail.setForeground(COLOR_TEXT_MAIN);
        taDetail.setFont(FONT_MONO);
        taDetail.setText("\n  Select a transaction to view receipt details...");
        rightPanel.add(new JScrollPane(taDetail), BorderLayout.CENTER);
        
        transactionTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < VehicleRentalSystem.transactions.size()) {
                RentalTransaction t = VehicleRentalSystem.transactions.get(selectedRow);
                taDetail.setText(t.getReceiptDetails(false));
            } else {
                taDetail.setText("\n  Select a transaction to view receipt details...");
            }
        });
        
        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        refreshTransactionTable();
        
        return panel;
    }

    private void refreshTransactionTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        for (RentalTransaction t : VehicleRentalSystem.transactions) {
            tableModel.addRow(new Object[]{
                t.getCustomerName(),
                t.getMobileNumber(),
                t.getVehicleCode(),
                t.getVehicle().getModel(),
                t.getTotalDuration() + " Days",
                t.isReturned() ? "Returned" : "Active"
            });
        }
    }

    private void addGridComponent(JPanel panel, Component c, GridBagConstraints gbc, int x, int y, int width, int height, int fill) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = fill;
        panel.add(c, gbc);
    }

    private void styleTextField(JTextField field) {
        field.setBackground(COLOR_INPUT_BG);
        field.setForeground(COLOR_TEXT_MAIN);
        field.setCaretColor(COLOR_TEXT_MAIN);
        field.setFont(FONT_INPUT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_INPUT_BG.brighter(), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(COLOR_TEXT_MUTED);
        label.setFont(FONT_LABEL);
        return label;
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(COLOR_INPUT_BG);
        comboBox.setForeground(COLOR_TEXT_MAIN);
        comboBox.setFont(FONT_INPUT);
        comboBox.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(COLOR_CARD_BG);
        checkBox.setForeground(COLOR_TEXT_MAIN);
        checkBox.setFont(FONT_LABEL);
        checkBox.setFocusPainted(false);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(FONT_LABEL);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
