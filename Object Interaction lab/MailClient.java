public class MailClient {
    private MailServer server;  // connection to the server
    private String user;        // this client’s username

    // Constructor
    public MailClient(MailServer server, String user) {
        this.server = server;
        this.user = user;
    }

    // Send a message
    public void sendMail(String to, String message) {
        MailItem item = new MailItem(user, to, message);
        server.post(item);
    }

    // Receive the next message
    public void getNextMail() {
        MailItem item = server.getNextMail(user);
        if (item == null) {
            System.out.println("No new mail for " + user);
        } else {
            System.out.println("New mail for " + user + ":");
            item.print();
        }
    }
}
