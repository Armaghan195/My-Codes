import java.util.Map;
import java.util.Scanner;

/**
 * BankAdministrator.java
 *
 * Simple admin interface (console).
 * Hardcoded credentials: admin / password
 *
 * Admin functions:
 *  - View All Customers
 *  - View All Accounts
 *  - Create New Account for existing customer
 *  - Unblock customer
 */
public class BankAdministrator {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "password";

    private final Bank bank;
    private final Scanner scanner;

    public BankAdministrator(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Enter admin username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Enter admin password: ");
        String pass = scanner.nextLine().trim();

        if (!ADMIN_USER.equals(user) || !ADMIN_PASS.equals(pass)) {
            System.out.println("Invalid admin credentials.");
            return;
        }
        System.out.println("Admin login successful.");
        menu();
    }

    private void menu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1) View All Customers");
            System.out.println("2) View All Accounts");
            System.out.println("3) Create New Account for Customer");
            System.out.println("4) Unblock Customer");
            System.out.println("0) Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) viewAllCustomers();
            else if (choice.equals("2")) viewAllAccounts();
            else if (choice.equals("3")) createAccountForCustomer();
            else if (choice.equals("4")) unblockCustomer();
            else if (choice.equals("0")) { System.out.println("Admin logged out."); break; }
            else System.out.println("Invalid option.");
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n-- Customers --");
        Map<String, Customer> all = bank.getAllCustomers();
        if (all.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        for (Customer c : all.values()) {
            System.out.print(c.getCustomerId() + " : " + c.getName() + " | Accounts: ");
            if (c.getAccounts().isEmpty()) {
                System.out.println("none");
            } else {
                for (BankAccount a : c.getAccounts()) {
                    System.out.print(a.getAccountNumber() + " ");
                }
                System.out.println();
            }
        }
    }

    private void viewAllAccounts() {
        System.out.println("\n-- All Accounts --");
        Map<String, BankAccount> all = bank.getAllAccounts();
        if (all.isEmpty()) {
            System.out.println("No accounts.");
            return;
        }
        for (BankAccount a : all.values()) {
            System.out.println(a.getAccountNumber() + " | Type: " + a.getClass().getSimpleName()
                    + " | Owner: " + a.getCustomerId() + " | Balance: " + String.format("%.2f", a.getBalance())
                    + " | Status: " + a.getStatus());
        }
    }

    private void createAccountForCustomer() {
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine().trim();
        Customer c = bank.getCustomerById(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.print("Choose Account Type (1. Savings | 2. Checking): ");
        int type = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter initial deposit: ");
        double dep = Double.parseDouble(scanner.nextLine().trim());

        if (type == 1) {
            System.out.print("Enter minimum balance for savings account: ");
            double minBal = Double.parseDouble(scanner.nextLine().trim());
            BankAccount acc = bank.createSavingsAccount(c.getCustomerId(), dep, minBal);
            System.out.println("Savings account created: " + acc.getAccountNumber());
        } else {
            System.out.print("Enter overdraft limit for checking account: ");
            double od = Double.parseDouble(scanner.nextLine().trim());
            BankAccount acc = bank.createCheckingAccount(c.getCustomerId(), dep, od);
            System.out.println("Checking account created: " + acc.getAccountNumber());
        }
    }

    private void unblockCustomer() {
        System.out.print("Enter customer ID to unblock: ");
        String id = scanner.nextLine().trim();
        boolean ok = bank.unblockCustomer(id);
        if (ok) System.out.println("Customer " + id + " unblocked.");
        else System.out.println("Customer not found or could not be unblocked.");
    }
}
