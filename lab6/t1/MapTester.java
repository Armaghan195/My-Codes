package t1;
public class MapTester {
    private PhoneBook phoneBook;

    
    public MapTester() {
        phoneBook = new PhoneBook();
    }

    
    public void test() {
        // Add entries
        phoneBook.enterNumber("Omen", "12345");
        phoneBook.enterNumber("Victus", "67890");
        phoneBook.enterNumber("Asus", "55555");

        // Lookup examples
        String number = phoneBook.lookupNumber("Omen");
        System.out.println("Omen's number is: " + number);

        number = phoneBook.lookupNumber("Alienware");
        if (number == null)
            System.out.println("Alienware not found in phone book.");

        // Duplicate key (same name)
        phoneBook.enterNumber("Omen", "99999");

        
        phoneBook.enterNumber("Victus", "67890");

        // Display all entries
        phoneBook.displayAll();
    }
}

