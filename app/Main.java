import database.*;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import model.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws PasswordMismatchException, InvalidPrimaryKeyException {
        System.out.println("Book system V2");

        JDBCBroker db = new JDBCBroker();
        db.getConnection();


        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("1. Add a Book");
        System.out.println("2. Add a Patron");
        System.out.println("3. Search Boo1ks by Title");
        System.out.println("4. Sort Books by Year");
        System.out.println("5. Search Patrons by Birthday");
        System.out.println("6. Search Patrons by Zip");
        System.out.println("7. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                addBook(scanner);
                break;
            case 2:
                addPatron(scanner);
                break;
            case 3:
                searchBooksByTitle(scanner);
                break;
            case 4:
                searchBooksOlderThan(scanner);
                break;
            case 5:
                searchPatronsYoungerThan(scanner);
                break;
            case 6:
                searchPatronsByZip(scanner);
                break;
            case 7:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addBook(Scanner scanner) {
        // Create a Properties object to hold the book data
        Properties bookProps = new Properties();

        // Prompt the user for book details
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        bookProps.setProperty("bookTitle", title);

        System.out.println("Enter author:");
        String author = scanner.nextLine();
        bookProps.setProperty("author", author);

        System.out.println("Enter publication year:");
        String pubYear = scanner.nextLine();
        bookProps.setProperty("pubYear", pubYear);

        // Set the default status to "Active"
        bookProps.setProperty("status", "Available");
        // Create a Book object
        Book newBook = new Book(bookProps);

        // Save the book to the database
        newBook.updateStateInDatabase();

        // Print the update status message
        System.out.println(newBook.getState("UpdateStatusMessage"));
    }
    private static void addPatron(Scanner scanner) {
        // Create a Properties object to hold the book data
        Properties patronProps = new Properties();

        // Prompt the user for patron details
        System.out.println("Enter patron details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();
        patronProps.setProperty("name", name); // Store name in Properties

        System.out.print("Address: ");
        String address = scanner.nextLine();
        patronProps.setProperty("address", address); // Store address in Properties

        System.out.print("City: ");
        String city = scanner.nextLine();
        patronProps.setProperty("city", city); // Store city in Properties

        System.out.print("State Code (2 characters, e.g., CA): ");
        String stateCode = scanner.nextLine();
        patronProps.setProperty("stateCode", stateCode); // Store stateCode in Properties

        System.out.print("ZIP Code (5 characters): ");
        String zip = scanner.nextLine();
        patronProps.setProperty("zip", zip); // Store zip in Properties

        System.out.print("Email: ");
        String email = scanner.nextLine();
        patronProps.setProperty("email", email); // Store email in Properties

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();
        patronProps.setProperty("dateOfBirth", dateOfBirth); // Store dateOfBirth in Properties


        // Validate input
        if (stateCode.length() != 2) {
            System.out.println("Error: State code must be exactly 2 characters.");
            return;
        }
        if (zip.length() != 5) {
            System.out.println("Error: ZIP code must be exactly 5 characters.");
            return;
        }

        // Set the default status to "Active"
        patronProps.setProperty("status", "Active");
        // Create a Book object
        Patron newPatron = new Patron(patronProps);

        // Save the book to the database2
        newPatron.updateStateInDatabase();

        // Print the update status message
        System.out.println(newPatron.getState("UpdateStatusMessage"));
    }


    private static void searchBooksByTitle(Scanner scanner) {
        System.out.print("Enter a book title (or part of it) to search: ");
        String searchTerm = scanner.nextLine();
        BookCollection book_result = new BookCollection(); // Assuming Book has a default constructor

        Vector<Book> books = book_result.findBooksWithTitleLike(searchTerm);

        if (books.isEmpty()) {
            System.out.println("No books found with title containing: " + searchTerm);
        } else {
            System.out.println("Books found with title containing '" + searchTerm + "':");
            for (Book b : books) {
                System.out.println("Title: " + b.getState("bookTitle") + ", Author: " + b.getState("author"));
            }
        }
    }
    private static void searchBooksOlderThan(Scanner scanner) {
        System.out.print("Enter a maximum year for the publish date: ");
        String searchTerm = scanner.nextLine();
        BookCollection book_result = new BookCollection(); // Assuming Book has a default constructor

        Vector<Book> books = book_result.findBooksOlderThanDate(searchTerm);

        if (books.isEmpty()) {
            System.out.println("No books found older than: " + searchTerm);
        } else {
            System.out.println("Books found: '" + searchTerm + "':");
            for (Book b : books) {
                System.out.println("Title: " + b.getState("bookTitle") + ", Author: " + b.getState("author") + ", Year: " + b.getState("pubYear"));
            }
        }
    }

    private static void searchPatronsYoungerThan(Scanner scanner) {
        System.out.print("Look for patrons born after: ");
        String searchTerm = scanner.nextLine();
        PatronCollection patron_result = new PatronCollection(); // Assuming Book has a default constructor

        Vector<Patron> patrons = patron_result.findPatronsYoungerThan(searchTerm);
        if (patrons.isEmpty()) {
            System.out.println("No patrons found younger than date: " + searchTerm);
        } else {
            System.out.println("Patrons born after: '" + searchTerm + "':");
            for (Patron b : patrons) {
                System.out.println("name: " + b.getState("name") + ", Birthday: " + b.getState("dateOfBirth"));
            }
        }
    }
    private static void searchPatronsByZip(Scanner scanner) {
        System.out.print("Look for patrons in ZIP: ");
        String searchTerm = scanner.nextLine();
        PatronCollection patron_result = new PatronCollection(); // Assuming Book has a default constructor

        Vector<Patron> patrons = patron_result.findPatronsAtZipCode(searchTerm);
        if (patrons.isEmpty()) {
            System.out.println("No patrons found within this ZIP: " + searchTerm);
        } else {
            System.out.println("Patrons located in: '" + searchTerm + "':");
            for (Patron b : patrons) {
                System.out.println("name: " + b.getState("name") + ", ZIP code: " + b.getState("zip"));
            }
        }
    }
}