public class Book {
    private String title;
    private String author;
    private String genre;
    private boolean availability;
    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = true;
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

    public boolean isAvailable() {
        return availability;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
   @Override
   public String toString() {
        return title + " by " + author;
    }
}
