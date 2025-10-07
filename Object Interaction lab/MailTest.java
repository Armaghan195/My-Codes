
public class MailTest {
    public static void main(String[] args) {
        MailServer server = new MailServer();

        MailClient alice = new MailClient(server, "Alice");
        MailClient bob = new MailClient(server, "Bob");

        // Alice sends mail to Bob
        alice.sendMail("Bob", "Hello Bob! How are you?");
        // Bob checks his mail
        bob.getNextMail();

        // Bob replies to Alice
        bob.sendMail("Alice", "Hi Alice! I'm fine, thanks!");
        // Alice checks her mail
        alice.getNextMail();
    }
}
