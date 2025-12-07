public class Video extends MediaItem {
    private String director;

    public Video(String title, String director, int playingTime, String comment) {
        super(title, playingTime, comment);
        this.director = director;
    }

    public Video(String title, String director, int playingTime) {
        this(title, director, playingTime, "");
    }

    public String getDirector() {
        return director;
    }

    @Override
    public void print() {
        System.out.println("Video: ");
        super.print();
        System.out.println("Director: " + director);
        System.out.println("-----------------------------");
    }
}
