
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private int quantity; // Quantity of the book
    private boolean isBorrowed; // Whether the book is currently borrowed
    private String borrowerName; // Name of the person who borrowed the book

    public Book(String title, String author, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.isBorrowed = false;
        this.borrowerName = null;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public boolean isBorrowed() { return isBorrowed; }
    public void borrowBook(String borrowerName) {
        if (quantity > 0) {
            this.isBorrowed = true;
            this.borrowerName = borrowerName;
            this.quantity--; // Decrease quantity when borrowed
        }
    }
    public void returnBook() {
        this.isBorrowed = false;
        this.borrowerName = null;
        this.quantity++; // Increase quantity when returned
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn +
               ", Available Quantity: " + quantity +
               ", Borrowed: " + (isBorrowed ? "Yes (By: " + borrowerName + ")" : "No");
    }
}

public class LibraryManagementSystem {
    private static ArrayList<Book> library = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> searchBook();
                case 4 -> displayAllBooks();
                case 5 -> borrowBook();
                case 6 -> returnBook();
                case 7 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        library.add(new Book(title, author, isbn, quantity));
        System.out.println("Book added successfully!");
    }

    private static void removeBook() {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        library.removeIf(book -> book.getIsbn().equals(isbn));
        System.out.println("Book removed successfully!");
    }

    private static void searchBook() {
        System.out.print("Enter title, author, or ISBN to search: ");
        String query = scanner.nextLine();
        boolean found = false;

        for (Book book : library) {
            if (book.getTitle().contains(query) || book.getAuthor().contains(query) || book.getIsbn().equals(query)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    private static void displayAllBooks() {
        if (library.isEmpty()) {
            System.out.println("The library is empty.");
        } else {
            for (Book book : library) {
                System.out.println(book);
            }
        }
    }

    private static void borrowBook() {
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter your name: ");
        String borrowerName = scanner.nextLine();

        for (Book book : library) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getQuantity() > 0) {
                    book.borrowBook(borrowerName);
                    System.out.println("Book borrowed successfully by " + borrowerName + "!");
                } else {
                    System.out.println("Sorry, this book is out of stock.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private static void returnBook() {
        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();

        for (Book book : library) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isBorrowed()) {
                    book.returnBook();
                    System.out.println("Book returned successfully!");
                } else {
                    System.out.println("This book was not borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
}
