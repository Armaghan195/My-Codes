
public class MailItem {
    // Fields
    private String from;   // sender
    private String to;     // recipient
    private String message; // the email content

    // Constructor
    public MailItem(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    // Getters
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    // Method to display mail
    public void print() {
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Message: " + message);
    }
}
