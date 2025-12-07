public class CD extends MediaItem {
    private String artist;
    private int numberOfTracks;

    public CD(String title, String artist, int tracks, int playingTime, String comment) {
        super(title, playingTime, comment);
        this.artist = artist;
        this.numberOfTracks = tracks;
    }

    public CD(String title, String artist, int tracks, int playingTime) {
        this(title, artist, tracks, playingTime, "");
    }

    public String getArtist() {
        return artist;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    @Override
    public void print() {
        System.out.println("CD: ");
        super.print();
        System.out.println("Artist: " + artist);
        System.out.println("Tracks: " + numberOfTracks);
        System.out.println("-----------------------------");
    }
}
