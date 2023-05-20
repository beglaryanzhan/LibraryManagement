public class Library {
    private EndlessArray<Book> books;
    public Library() {
        books = new EndlessArray<>();
    }
    public void addBook(Book book) {
        books.addFirst(book);
    }
    public void removeBook(Book book) {
        books.remove(book);
    }
    public void listBooks() {
        for (int i = 0; i < books.getSize(); i++) {
            System.out.println(books.getAt(i).getTitle() + " by " + books.getAt(i).getAuthor());
        }
    }
    public EndlessArray<Book> displayBooks() {
        return books;
    }

}
