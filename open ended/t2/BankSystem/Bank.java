import java.io.*;
import java.util.*;

/**
 * Bank.java
 * 
 * Responsibilities:
 * - Manage customers and accounts in memory
 * - Provide create/find/unblock helpers for accounts and customers
 * - Load and save customers/accounts to human-readable files:
 *      customers.txt  -> format: customerId|name|pin
 *      accounts.txt   -> format: accountNumber|customerId|type|balance|minOrOverdraft|status
 */
public class Bank {
    private final Map<String, Customer> customers;    // key: customerId
    private final Map<String, BankAccount> accounts;  // key: accountNumber

    private final File customersFile = new File("customers.txt");
    private final File accountsFile = new File("accounts.txt");

    public Bank() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        loadData();
    }

    // -------------------------
    // Customer Registration
    // -------------------------
    public Customer registerCustomer(String name, String pin) {
        String id = generateCustomerId();
        Customer c = new Customer(id, name, pin);
        customers.put(id, c);
        saveData();
        return c;
    }

    public void registerCustomer(Customer c) {
        customers.put(c.getCustomerId(), c);
        saveData();
    }

    // -------------------------
    // Account Creation
    // -------------------------
    public BankAccount createSavingsAccount(String customerId, double initialDeposit, double minimumBalance) {
        Customer c = customers.get(customerId);
        if (c == null) throw new IllegalArgumentException("Customer not found: " + customerId);
        String accNum = "S-" + UUID.randomUUID().toString().substring(0, 8);
        SavingsAccount a = new SavingsAccount(accNum, customerId, initialDeposit, minimumBalance);
        accounts.put(accNum, a);
        c.addAccount(a);
        saveData();
        return a;
    }

    public BankAccount createCheckingAccount(String customerId, double initialDeposit, double overdraftLimit) {
        Customer c = customers.get(customerId);
        if (c == null) throw new IllegalArgumentException("Customer not found: " + customerId);
        String accNum = "Ck-" + UUID.randomUUID().toString().substring(0, 8);
        CheckingAccount a = new CheckingAccount(accNum, customerId, initialDeposit, overdraftLimit);
        accounts.put(accNum, a);
        c.addAccount(a);
        saveData();
        return a;
    }

    // -------------------------
    // Authentication
    // -------------------------
    public Customer authenticate(String customerId, String pin) throws AccountBlockedException {
        Customer c = customers.get(customerId);
        if (c == null) return null;
        if (c.isBlocked()) throw new AccountBlockedException("Your account is blocked. Contact admin.");
        if (c.getPin().equals(pin)) {
            c.resetFailedAttempts();
            return c;
        } else {
            c.incrementFailedAttempts();
            if (c.getFailedAttempts() >= 3) {
                c.block();
                saveData();
                throw new AccountBlockedException("Too many failed attempts. Account blocked.");
            }
            return null;
        }
    }

    // -------------------------
    // Lookup
    // -------------------------
    public Customer getCustomerById(String id) {
        return customers.get(id);
    }

    public BankAccount getAccountByNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Map<String, Customer> getAllCustomers() {
        return customers;
    }

    public Map<String, BankAccount> getAllAccounts() {
        return accounts;
    }

    // -------------------------
    // Admin & Utility
    // -------------------------
    public boolean unblockCustomer(String customerId) {
        Customer c = customers.get(customerId);
        if (c == null) return false;
        c.unblock();
        saveData();
        return true;
    }

    public void printBankSummary() {
        System.out.println("=== BANK SUMMARY ===");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + accounts.size());
        for (Customer c : customers.values()) {
            System.out.println("Customer ID: " + c.getCustomerId() + " | Name: " + c.getName());
            for (BankAccount a : c.getAccounts()) {
                System.out.println("   → " + a.getAccountNumber() + " | " + a.getClass().getSimpleName()
                        + " | Balance: " + String.format("%.2f", a.getBalance()));
            }
        }
    }

    // -------------------------
    // Persistence
    // -------------------------
    private void loadData() {
        // load customers
        if (customersFile.exists()) {
            try (BufferedReader r = new BufferedReader(new FileReader(customersFile))) {
                String line;
                while ((line = r.readLine()) != null) {
                    String[] parts = line.split("\\|", -1);
                    if (parts.length < 3) continue;
                    String id = parts[0];
                    String name = parts[1];
                    String pin = parts[2];
                    Customer c = new Customer(id, name, pin);
                    customers.put(id, c);
                }
            } catch (IOException e) {
                System.out.println("Failed to load customers: " + e.getMessage());
            }
        }

        // load accounts
        if (accountsFile.exists()) {
            try (BufferedReader r = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                while ((line = r.readLine()) != null) {
                    String[] parts = line.split("\\|", -1);
                    if (parts.length < 6) continue;
                    String accNum = parts[0];
                    String customerId = parts[1];
                    String type = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    double special = Double.parseDouble(parts[4]);
                    String statusStr = parts[5];

                    Customer owner = customers.get(customerId);
                    if (owner == null) continue;

                    BankAccount acc;
                    if (type.equalsIgnoreCase("SAVINGS")) {
                        acc = new SavingsAccount(accNum, customerId, balance, special);
                    } else {
                        acc = new CheckingAccount(accNum, customerId, balance, special);
                    }
                    accounts.put(accNum, acc);
                    owner.addAccount(acc);
                }
            } catch (IOException e) {
                System.out.println("Failed to load accounts: " + e.getMessage());
            }
        }
    }

    public synchronized void saveData() {
        // customers
        try (BufferedWriter w = new BufferedWriter(new FileWriter(customersFile, false))) {
            for (Customer c : customers.values()) {
                w.write(c.getCustomerId() + "|" + escapePipe(c.getName()) + "|" + c.getPin());
                w.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save customers: " + e.getMessage());
        }

        // accounts
        try (BufferedWriter w = new BufferedWriter(new FileWriter(accountsFile, false))) {
            for (BankAccount a : accounts.values()) {
                String type = a instanceof SavingsAccount ? "SAVINGS" : "CHECKING";
                double special = (a instanceof SavingsAccount)
                        ? ((SavingsAccount) a).getMinimumBalance()
                        : ((CheckingAccount) a).getOverdraftLimit();
                w.write(a.getAccountNumber() + "|" + a.getCustomerId() + "|" + type + "|"
                        + String.format(Locale.US, "%.2f", a.getBalance()) + "|"
                        + String.format(Locale.US, "%.2f", special) + "|" + a.getStatus().name());
                w.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save accounts: " + e.getMessage());
        }
    }

    // -------------------------
    // Helpers
    // -------------------------
    private String generateCustomerId() {
        return "C-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String escapePipe(String s) {
        return s == null ? "" : s.replace("|", " ");
    }
}
