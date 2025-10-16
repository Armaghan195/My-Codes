import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String customerId;
    private final String name;
    private final String pin; // simple PIN for demo (should be stored hashed in real systems)
    private final List<BankAccount> accounts;

    // Security fields
    private int failedPinAttempts;
    private boolean blocked;

    public Customer(String customerId, String name, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
        this.accounts = new ArrayList<>();
        this.failedPinAttempts = 0;
        this.blocked = false;
    }

    // getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public boolean isBlocked() { return blocked; }

    public void addAccount(BankAccount account) { accounts.add(account); }
    public List<BankAccount> getAccounts() { return accounts; }

    // PIN check with blocking logic (example: block after 3 attempts)
    public boolean verifyPin(String attempt) {
        if (blocked) return false;
        if (this.pin.equals(attempt)) {
            failedPinAttempts = 0;
            return true;
        } else {
            failedPinAttempts++;
            if (failedPinAttempts >= 3) {
                blocked = true; // permanently blocked for this simple lab
            }
            return false;
        }
    }

    public void unblock() {
        blocked = false;
        failedPinAttempts = 0;
    }

    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}
