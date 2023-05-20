import java.util.Arrays;

public class User {
    private String name;
    private String address;
    private EndlessArray<Book> borrowingHistory;
    public User(String name, String address) {
        this.name = name;
        this.address = address;
        this.borrowingHistory = new EndlessArray<>();
    }
    public void borrowBook(Book book) {
        borrowingHistory.addFirst(book);
    }

    public void returnBook(Book book) {
        borrowingHistory.remove(book);
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public EndlessArray<Book> getBorrowingHistory() {
        return borrowingHistory;
    }
}
