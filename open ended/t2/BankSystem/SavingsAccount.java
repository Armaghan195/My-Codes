public class SavingsAccount extends BankAccount {
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, String customerId, double initialBalance, double minimumBalance) {
        super(accountNumber, customerId, initialBalance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public Transaction deposit(double amount) {
        if (amount <= 0) {
            Transaction tx = new Transaction(TransactionType.DEPOSIT, amount, null, this.accountNumber, TransactionStatus.FAILED_OTHER, "Invalid deposit amount");
            transactions.add(tx);
            return tx;
        }
        balance += amount;
        Transaction tx = new Transaction(TransactionType.DEPOSIT, amount, null, this.accountNumber, TransactionStatus.SUCCESS, "Deposit successful");
        transactions.add(tx);
        return tx;
    }

    @Override
    public Transaction withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            Transaction tx = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_OTHER, "Invalid withdrawal amount");
            transactions.add(tx);
            return tx;
        }

        if (balance - amount < minimumBalance) {
            Transaction tx = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS,
                    "Cannot withdraw: would breach minimum balance of " + minimumBalance);
            transactions.add(tx);
            throw new InsufficientFundsException("Withdrawal would breach minimum balance.");
        }

        balance -= amount;
        Transaction tx = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.SUCCESS, "Withdrawal successful");
        transactions.add(tx);
        return tx;
    }

    public double getMinimumBalance() { return minimumBalance; }
}
