import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String sourceAccount; // may be null for deposit
    private final String targetAccount; // may be null for withdraw
    private final TransactionStatus status;
    private final String note;

    public Transaction(TransactionType type, double amount,
                       String sourceAccount, String targetAccount,
                       TransactionStatus status, String note) {
        this.transactionId = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.status = status;
        this.note = note;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String toReceipt() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction ID: ").append(transactionId).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Amount: ").append(String.format("%.2f", amount)).append("\n");
        sb.append("Time: ").append(timestamp.format(fmt)).append("\n");
        sb.append("From: ").append(sourceAccount == null ? "-" : sourceAccount).append("\n");
        sb.append("To: ").append(targetAccount == null ? "-" : targetAccount).append("\n");
        sb.append("Status: ").append(status).append("\n");
        if (note != null && !note.isEmpty()) sb.append("Note: ").append(note).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "[" + transactionId + "] " + type + " " + String.format("%.2f", amount) +
               " | from: " + (sourceAccount==null?"-":sourceAccount) +
               " to: " + (targetAccount==null?"-":targetAccount) +
               " | " + status + " @ " + timestamp;
    }
}
