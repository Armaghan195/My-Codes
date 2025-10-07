
import java.util.*;

public class MailServer {
    // Each recipient has a list of mails
    private Map<String, List<MailItem>> mailbox;

    // Constructor
    public MailServer() {
        mailbox = new HashMap<>();
    }

    // Store a mail for a recipient
    public void post(MailItem item) {
        mailbox.putIfAbsent(item.getTo(), new ArrayList<>());
        mailbox.get(item.getTo()).add(item);
    }

    // Retrieve the next mail for a user
    public MailItem getNextMail(String user) {
        if (mailbox.containsKey(user) && !mailbox.get(user).isEmpty()) {
            return mailbox.get(user).remove(0);
        }
        return null; // no mail
    }
}
