import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private Date publishDate;
    private Date addedDate;

    public Book(String id, String title, String author, String genre, String publisher, Date publishDate, Date addedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.addedDate = addedDate;
    }

    // Getters and setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Date getPublishDate() { return publishDate; }
    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }

    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }

    @Override
    public String toString() {
        SimpleDateFormat publishDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat addedDateFormat = new SimpleDateFormat("dd-MM-yyyy|HH-mm-ss");
        return id + ";" + title + ";" + author + ";" + genre + ";" + publisher + ";" + publishDateFormat.format(publishDate) + ";" + addedDateFormat.format(addedDate);
    }
}
