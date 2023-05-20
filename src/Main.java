import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        Library newOne = new Library();
        Book zam = new Book("The Lord of the Rings","J.R.R Tolkien","Fantasy");
        newOne.addBook(zam);
        System.out.println(zam.getTitle());
        newOne.listBooks();

        Library library = new Library();

        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Classic");
        Book book2 = new Book("1984", "George Orwell", "Dystopian Fiction");
        Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        User user = new User("John Doe", "123 Main Street");

        user.borrowBook(book1);
        user.borrowBook(book3);

        System.out.println("User: " + user.getName());
        System.out.println("Address: " + user.getAddress());

        System.out.println("Borrowing History:" + user.getBorrowingHistory().toString());

        UI test = new UI();
    }
}