import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== DoME DATABASE MENU =====");
            System.out.println("1. Add CD");
            System.out.println("2. Add Video");
            System.out.println("3. Add Video Game");
            System.out.println("4. List all items");
            System.out.println("5. Search by title");
            System.out.println("6. Remove item by title");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear input buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter CD Title: ");
                    String cdTitle = sc.nextLine();
                    System.out.print("Enter Artist: ");
                    String artist = sc.nextLine();
                    System.out.print("Enter Track Count: ");
                    int tracks = sc.nextInt();
                    System.out.print("Enter Playtime (min): ");
                    int cdTime = sc.nextInt();
                    sc.nextLine();

                    CD cd = new CD(cdTitle, artist, tracks, cdTime);
                    db.addItem(cd);
                    System.out.println("CD Added!");
                    break;

                case 2:
                    System.out.print("Enter Video Title: ");
                    String vTitle = sc.nextLine();
                    System.out.print("Enter Director: ");
                    String director = sc.nextLine();
                    System.out.print("Enter Playtime (min): ");
                    int vTime = sc.nextInt();
                    sc.nextLine();

                    Video v = new Video(vTitle, director, vTime);
                    db.addItem(v);
                    System.out.println("Video Added!");
                    break;

                case 3:
                    System.out.print("Enter Game Title: ");
                    String gTitle = sc.nextLine();
                    System.out.print("Enter Platform: ");
                    String platform = sc.nextLine();
                    System.out.print("Enter Playtime (min): ");
                    int gTime = sc.nextInt();
                    sc.nextLine();

                    VideoGame game = new VideoGame(gTitle, platform, gTime);
                    db.addItem(game);
                    System.out.println("Video Game Added!");
                    break;

                case 4:
                    db.list();
                    break;

                case 5:
                    System.out.print("Enter title keyword: ");
                    String key = sc.nextLine();
                    db.searchByTitle(key);
                    break;

                case 6:
                    System.out.print("Enter full title to remove: ");
                    String removeKey = sc.nextLine();
                    db.removeByTitle(removeKey);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}


