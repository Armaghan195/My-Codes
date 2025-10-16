import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    protected final String accountNumber;
    protected double balance;
    protected final String customerId;
    protected AccountStatus status;
    protected final List<Transaction> transactions;

    public BankAccount(String accountNumber, String customerId, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initialBalance;
        this.status = AccountStatus.ACTIVE;
        this.transactions = new ArrayList<>();
    }

    // Basic getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getCustomerId() { return customerId; }
    public AccountStatus getStatus() { return status; }

    // Transaction history
    public List<Transaction> getTransactions() { return transactions; }

    // deposit and withdraw are abstract because business rules differ
    public abstract Transaction deposit(double amount);
    public abstract Transaction withdraw(double amount) throws InsufficientFundsException;

    // For transfer, we implement a default method that uses withdraw+deposit
    public Transaction transferTo(BankAccount toAccount, double amount) {
        // Attempt withdraw from this account and deposit to toAccount.
        Transaction withdrawTx;
        try {
            withdrawTx = this.withdraw(amount);
            if (withdrawTx.getStatus() != TransactionStatus.SUCCESS) {
                // create failed transfer transaction
                Transaction failed = new Transaction(TransactionType.TRANSFER, amount, this.accountNumber, toAccount.getAccountNumber(),
                        TransactionStatus.FAILED_OTHER, "Withdrawal part failed");
                transactions.add(failed);
                return failed;
            }
        } catch (InsufficientFundsException e) {
            Transaction failed = new Transaction(TransactionType.TRANSFER, amount, this.accountNumber, toAccount.getAccountNumber(),
                    TransactionStatus.FAILED_INSUFFICIENT_FUNDS, e.getMessage());
            transactions.add(failed);
            return failed;
        }

        // Deposit into toAccount (create a record)
        Transaction depositTx = toAccount.deposit(amount);
        Transaction tx = new Transaction(TransactionType.TRANSFER, amount, this.accountNumber, toAccount.getAccountNumber(),
                TransactionStatus.SUCCESS, "Transfer completed");
        this.transactions.add(tx);
        toAccount.getTransactions().add(tx);
        return tx;
    }

    public void blockAccount() { this.status = AccountStatus.BLOCKED; }
    public void activateAccount() { this.status = AccountStatus.ACTIVE; }
}
