import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Database {
    private static final String DATA_FILE = "database.txt";
    private ArrayList<MediaItem> items;

    public Database() {
        items = new ArrayList<>();
        loadFromFile();
    }

    public void add(MediaItem item) {
        items.add(item);
    }

    // Convenience wrapper used by the menu code
    public void addItem(MediaItem item) {
        add(item);
        saveToFile();
    }

    public void list() {
        if (items.isEmpty()) {
            System.out.println("No items in database.");
            return;
        }

        for (MediaItem item : items) {
            item.print();
        }
    }

    public void searchByTitle(String keyword) {
        boolean found = false;
        for (MediaItem item : items) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                item.print();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items match that title keyword.");
        }
    }

    public void removeByTitle(String fullTitle) {
        boolean removed = items.removeIf(item -> item.getTitle().equalsIgnoreCase(fullTitle));
        if (removed) {
            System.out.println("Item removed.");
            saveToFile();
        } else {
            System.out.println("No item found with that title.");
        }
    }

    private void loadFromFile() {
        Path path = Path.of(DATA_FILE);
        if (!Files.exists(path)) {
            return; // nothing to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\t", -1);
                MediaItem item = parseItem(parts);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load saved data: " + e.getMessage());
        }
    }

    private MediaItem parseItem(String[] parts) {
        try {
            String type = parts[0];
            switch (type) {
                case "CD":
                    if (parts.length < 5) {
                        return null;
                    }
                    return new CD(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts.length > 5 ? parts[5] : "");
                case "Video":
                    if (parts.length < 4) {
                        return null;
                    }
                    return new Video(parts[1], parts[2], Integer.parseInt(parts[3]), parts.length > 4 ? parts[4] : "");
                case "VideoGame":
                    if (parts.length < 4) {
                        return null;
                    }
                    return new VideoGame(parts[1], parts[2], Integer.parseInt(parts[3]), parts.length > 4 ? parts[4] : "");
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null; // skip malformed line
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (MediaItem item : items) {
                writer.write(serialize(item));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    private String serialize(MediaItem item) {
        if (item instanceof CD) {
            CD cd = (CD) item;
            return String.join("\t", "CD", cd.getTitle(), cd.getArtist(), String.valueOf(cd.getNumberOfTracks()), String.valueOf(cd.getPlayingTime()), cd.getComment());
        }
        if (item instanceof Video) {
            Video v = (Video) item;
            return String.join("\t", "Video", v.getTitle(), v.getDirector(), String.valueOf(v.getPlayingTime()), v.getComment());
        }
        if (item instanceof VideoGame) {
            VideoGame g = (VideoGame) item;
            return String.join("\t", "VideoGame", g.getTitle(), g.getPlatform(), String.valueOf(g.getPlayingTime()), g.getComment());
        }
        return "";
    }
}
