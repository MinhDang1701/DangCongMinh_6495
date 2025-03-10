package novelreadergui;

/**
 * Class story with title, author, genre, and content.
 */
public class Story {

    private String title;
    private String author;
    private String genre;
    private String content;

    public Story(String title, String author, String genre, String content) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title + " - " + author + " (" + genre + ")";
    }
}
