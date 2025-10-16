import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank {
    private final Map<String, Customer> customers;    // key: customerId
    private final Map<String, BankAccount> accounts;  // key: accountNumber

    public Bank() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        initializeSampleData(); // create sample customers for demo
    }

    // Register new customer
    public Customer registerCustomer(String name, String pin) {
        String id = "C-" + UUID.randomUUID().toString().substring(0, 8);
        Customer c = new Customer(id, name, pin);
        customers.put(id, c);
        return c;
    }

    // Create account and attach to customer
    public BankAccount createSavingsAccount(String customerId, double initialDeposit, double minimumBalance) {
        String accNum = "S-" + UUID.randomUUID().toString().substring(0, 8);
        SavingsAccount a = new SavingsAccount(accNum, customerId, initialDeposit, minimumBalance);
        accounts.put(accNum, a);
        customers.get(customerId).addAccount(a);
        return a;
    }

    public BankAccount createCheckingAccount(String customerId, double initialDeposit, double overdraftLimit) {
        String accNum = "Ck-" + UUID.randomUUID().toString().substring(0, 8);
        CheckingAccount a = new CheckingAccount(accNum, customerId, initialDeposit, overdraftLimit);
        accounts.put(accNum, a);
        customers.get(customerId).addAccount(a);
        return a;
    }

    // lookups
    public Customer getCustomerById(String id) {
        return customers.get(id);
    }

    public BankAccount getAccountByNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Authentication (by customerId + PIN)
    public Customer authenticate(String customerId, String pin) throws AccountBlockedException {
        Customer c = customers.get(customerId);
        if (c == null) return null;
        if (c.isBlocked()) throw new AccountBlockedException("Account is blocked due to multiple failed PIN attempts.");
        boolean ok = c.verifyPin(pin);
        if (!ok) {
            if (c.isBlocked()) throw new AccountBlockedException("Account has been blocked after multiple failed attempts.");
            return null;
        }
        return c;
    }

    // For demo: initialize sample customers and accounts
    private void initializeSampleData() {
        Customer alice = registerCustomer("Alice", "1111");
        Customer bob = registerCustomer("Bob", "2222");

        createSavingsAccount(alice.getCustomerId(), 1000.0, 100.0);
        createCheckingAccount(alice.getCustomerId(), 200.0, 500.0);

        createCheckingAccount(bob.getCustomerId(), 50.0, 200.0);
        createSavingsAccount(bob.getCustomerId(), 500.0, 50.0);
    }

    // helper to print bank summary (for admin)
    public void printBankSummary() {
        System.out.println("=== Bank Summary ===");
        for (Customer c : customers.values()) {
            System.out.println(c);
            for (BankAccount a : c.getAccounts()) {
                System.out.println("  - " + a.getAccountNumber() + " : " + a.getClass().getSimpleName() + " | Balance: " + String.format("%.2f", a.getBalance()));
            }
        }
        System.out.println("====================");
    }
}
