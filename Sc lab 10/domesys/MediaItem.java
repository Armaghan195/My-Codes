public class MediaItem {
    private String title;
    private int playingTime;
    private boolean gotIt;
    private String comment;

    public MediaItem(String title, int playingTime, String comment) {
        this.title = title;
        this.playingTime = playingTime;
        this.comment = comment;
        this.gotIt = false;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setOwn(boolean own) {
        this.gotIt = own;
    }

    public String getTitle() {
        return title;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public String getComment() {
        return comment;
    }

    public void print() {
        System.out.println("Title: " + title);
        System.out.println("Playing Time: " + playingTime + " mins");
        System.out.println("Own it? " + gotIt);
        System.out.println("Comment: " + comment);
    }
}
