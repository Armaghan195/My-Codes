import java.util.Scanner;

/**
 * Main.java
 *
 * Updated main entrypoint:
 * - Loads bank (Bank constructor loads data)
 * - Provides menu for registering customers, adding accounts, launching ATM, and admin login
 * - On exit, explicitly saves data
 */
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(); // loads from files in constructor
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Bank System (Full Bank version)");
        while (true) {
            System.out.println("\n====== BANKING SYSTEM MENU ======");
            System.out.println("1. Register New Customer (auto-generated ID)");
            System.out.println("2. Add Account to Existing Customer");
            System.out.println("3. Login to ATM");
            System.out.println("4. Show Bank Summary");
            System.out.println("5. Admin Login");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine().trim();

            if (input.equals("1")) {
                System.out.print("Enter Name: ");
                String name = sc.nextLine().trim();
                System.out.print("Set a PIN: ");
                String pin = sc.nextLine().trim();

                Customer c = bank.registerCustomer(name, pin);
                System.out.println("Customer created. Customer ID = " + c.getCustomerId());

                System.out.print("Create an account now? (y/n): ");
                String yn = sc.nextLine().trim();
                if (yn.equalsIgnoreCase("y")) {
                    System.out.print("Choose Account Type (1. Savings | 2. Checking): ");
                    int type = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter initial deposit: ");
                    double dep = Double.parseDouble(sc.nextLine().trim());

                    if (type == 1) {
                        System.out.print("Enter minimum balance for this savings account: ");
                        double minBal = Double.parseDouble(sc.nextLine().trim());
                        BankAccount a = bank.createSavingsAccount(c.getCustomerId(), dep, minBal);
                        System.out.println("Savings account created: " + a.getAccountNumber());
                    } else {
                        System.out.print("Enter overdraft limit for checking account: ");
                        double od = Double.parseDouble(sc.nextLine().trim());
                        BankAccount a = bank.createCheckingAccount(c.getCustomerId(), dep, od);
                        System.out.println("Checking account created: " + a.getAccountNumber());
                    }
                }

            } else if (input.equals("2")) {
                System.out.print("Enter existing Customer ID: ");
                String custId = sc.nextLine().trim();
                Customer c = bank.getCustomerById(custId);
                if (c == null) {
                    System.out.println("Customer not found.");
                    continue;
                }
                System.out.print("Choose Account Type (1. Savings | 2. Checking): ");
                int type = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter initial deposit: ");
                double dep = Double.parseDouble(sc.nextLine().trim());
                if (type == 1) {
                    System.out.print("Enter minimum balance for this savings account: ");
                    double minBal = Double.parseDouble(sc.nextLine().trim());
                    BankAccount a = bank.createSavingsAccount(c.getCustomerId(), dep, minBal);
                    System.out.println("Savings account created: " + a.getAccountNumber());
                } else {
                    System.out.print("Enter overdraft limit for checking account: ");
                    double od = Double.parseDouble(sc.nextLine().trim());
                    BankAccount a = bank.createCheckingAccount(c.getCustomerId(), dep, od);
                    System.out.println("Checking account created: " + a.getAccountNumber());
                }

            } else if (input.equals("3")) {
                ATM atm = new ATM(bank);
                atm.start();

            } else if (input.equals("4")) {
                bank.printBankSummary();

            } else if (input.equals("5")) {
                BankAdministrator admin = new BankAdministrator(bank);
                admin.start();

            } else if (input.equals("6")) {
                System.out.println("Exiting. Goodbye!");
                bank.saveData(); // persist before exit
                sc.close();
                return;

            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

