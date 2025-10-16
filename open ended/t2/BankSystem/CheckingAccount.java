public class CheckingAccount extends BankAccount {
    private final double overdraftLimit; // positive number, allowed negative balance up to -overdraftLimit

    public CheckingAccount(String accountNumber, String customerId, double initialBalance, double overdraftLimit) {
        super(accountNumber, customerId, initialBalance);
        this.overdraftLimit = overdraftLimit;
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

        if (balance - amount < -overdraftLimit) {
            Transaction tx = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS,
                    "Overdraft limit exceeded. Limit: " + overdraftLimit);
            transactions.add(tx);
            throw new InsufficientFundsException("Overdraft limit exceeded.");
        }

        balance -= amount;
        Transaction tx = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.SUCCESS, "Withdrawal successful");
        transactions.add(tx);
        return tx;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
}
