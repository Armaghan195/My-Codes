import java.util.HashMap;
import java.util.Map;

public class SupportResponder {

    private Map<String, String> responses;

    
    public SupportResponder() {
        responses = new HashMap<>();

        // common keywords and responses
        responses.put("printer", "Try checking the printer cables or restarting it.");
        responses.put("internet", "Please check your internet connection or restart your router.");
        responses.put("slow", "Close unused applications to improve system performance.");
        responses.put("password", "You can reset your password using the 'Forgot Password' option.");
        responses.put("email", "Check your spam folder or verify your email settings.");
        responses.put("crash", "Please restart the program. If it continues, reinstall the application.");
    }

    
    public String getResponse(String input) {
        input = input.toLowerCase();

        
        for (String keyword : responses.keySet()) {
            if (input.contains(keyword)) {
                return responses.get(keyword);
            }
        }

        // Default response 
        return "I'm not sure I understand. Could you please provide more details?";
    }
}
