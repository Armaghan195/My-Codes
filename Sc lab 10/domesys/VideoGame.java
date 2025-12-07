public class VideoGame extends MediaItem {
    private String platform;

    public VideoGame(String title, String platform, int playingTime, String comment) {
        super(title, playingTime, comment);
        this.platform = platform;
    }

    public VideoGame(String title, String platform, int playingTime) {
        this(title, platform, playingTime, "");
    }

    public String getPlatform() {
        return platform;
    }

    @Override
    public void print() {
        System.out.println("Video Game: ");
        super.print();
        System.out.println("Platform: " + platform);
        System.out.println("-----------------------------");
    }
}
