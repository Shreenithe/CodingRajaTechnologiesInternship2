public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true; // Initially available
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Available: " + available;
    }
}
public class Patron {
    private int patronId;
    private String name;
    private List<Book> borrowedBooks;

    public Patron(int patronId, String name) {
        this.patronId = patronId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Patron ID: " + patronId + ", Name: " + name;
    }
}
import java.util.*;

public class Library {
    private List<Book> books;
    private List<Patron> patrons;
    private Map<Patron, List<Book>> borrowedBooks;

    public Library() {
        books = new ArrayList<>();
        patrons = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    public void borrowBook(Patron patron, Book book) {
        if (!book.isAvailable()) {
            System.out.println("Sorry, the book '" + book.getTitle() + "' is not available.");
            return;
        }
        
        patron.borrowBook(book);
        book.setAvailable(false);
        
        if (!borrowedBooks.containsKey(patron)) {
            borrowedBooks.put(patron, new ArrayList<>());
        }
        borrowedBooks.get(patron).add(book);
        
        System.out.println("Patron '" + patron.getName() + "' has borrowed the book '" + book.getTitle() + "'.");
    }

    public void returnBook(Patron patron, Book book) {
        if (!patron.getBorrowedBooks().contains(book)) {
            System.out.println("This book is not borrowed by the patron.");
            return;
        }
        
        patron.returnBook(book);
        book.setAvailable(true);
        
        borrowedBooks.get(patron).remove(book);
        
        System.out.println("Patron '" + patron.getName() + "' has returned the book '" + book.getTitle() + "'.");
    }

    public void displayBooks() {
        System.out.println("\n--- List of Books ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayPatrons() {
        System.out.println("\n--- List of Patrons ---");
        for (Patron patron : patrons) {
            System.out.println(patron);
        }
    }

    public void displayBorrowedBooks() {
        System.out.println("\n--- Borrowed Books ---");
        for (Map.Entry<Patron, List<Book>> entry : borrowedBooks.entrySet()) {
            Patron patron = entry.getKey();
            List<Book> books = entry.getValue();
            
            System.out.println("Patron: " + patron.getName());
            for (Book book : books) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }

    public static void main(String[] args) {
        Library library = new Library();

        // Adding books
        library.addBook(new Book(1, "Java Programming", "John Doe"));
        library.addBook(new Book(2, "Data Structures", "Jane Smith"));
        library.addBook(new Book(3, "Database Management", "Michael Johnson"));

        // Adding patrons
        library.addPatron(new Patron(101, "Alice Brown"));
        library.addPatron(new Patron(102, "Bob Green"));

        // Borrowing books
        library.borrowBook(library.patrons.get(0), library.books.get(0)); // Alice borrows Java Programming
        library.borrowBook(library.patrons.get(1), library.books.get(1)); // Bob borrows Data Structures
        library.borrowBook(library.patrons.get(0), library.books.get(2)); // Alice borrows Database Management

        // Displaying borrowed books
        library.displayBorrowedBooks();

        // Returning books
        library.returnBook(library.patrons.get(0), library.books.get(0)); // Alice returns Java Programming

        // Displaying borrowed books after return
        library.displayBorrowedBooks();
    }
}
