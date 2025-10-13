package t1;
import java.util.HashMap;

public class PhoneBook {
    private HashMap<String, String> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<>();
    }

    // Add entry
    public void enterNumber(String name, String number) {
        phoneBook.put(name, number);
        System.out.println("Added: " + name + " → " + number);
    }

    // Lookup number by name
    public String lookupNumber(String name) {
        return phoneBook.get(name);
    }

    // Display all contacts
    public void displayAll() {
        System.out.println("\nPhone Book Entries:");
        for (String name : phoneBook.keySet()) {
            System.out.println(name + " → " + phoneBook.get(name));
        }
    }
}
