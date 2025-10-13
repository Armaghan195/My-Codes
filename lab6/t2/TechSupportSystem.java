import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TechSupportSystem {

    private SupportResponder responder;
    private List<String> conversationHistory;

    
    public TechSupportSystem() {
        responder = new SupportResponder();
        conversationHistory = new ArrayList<>();
    }

   
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tech Support System!");
        System.out.println("Type 'bye' to end the chat.\n");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Bot: Thank you for contacting support. Goodbye!");
                break;
            }

            // Store the user input in history
            conversationHistory.add("You: " + userInput);

            // bot response
            String response = responder.getResponse(userInput);
            System.out.println("Bot: " + response);

            // bot response in history
            conversationHistory.add("Bot: " + response);
        }

        scanner.close();
        showHistory();
    }

    // Display the full conversation history
    public void showHistory() {
        System.out.println("\n--- Conversation History ---");
        for (String line : conversationHistory) {
            System.out.println(line);
        }
        System.out.println("-----------------------------");
    }
}
