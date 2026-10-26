import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A config-based UI system demonstrating the usage of our custom TreeMap class
 *
 * @author Ace Breaux
 * @version 5-21-2025
 */
public class Main {
    // The TreeMap containing the book data
    private static TreeMap<String, Book> library;

    // Load data & initiate UI system
    public static void main(String[] args) {
        // Attempt to load datafile into a Scanner object
        Scanner bookFile;
        try {
            bookFile = new Scanner(new File("BooksDataFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("BooksDataFile.txt was not found; terminating program");
            return;
        }

        // Move scanner to the right line (skip header)
        bookFile.nextLine();

        // Create a TreeMap to hold the data
        library = new TreeMap<>();

        // Load data from file into a TreeMap
        while (bookFile.hasNextLine()) {
            String[] tempArray = bookFile.nextLine().split("~");

            // Create book from data and add it to the TreeMap with its associated key
            library.put(tempArray[2],    // Key: ISBN
                    new Book(            // Value: Book record
                    tempArray[2],                       // ISBN (String)
                    tempArray[3],                       // Authors (String)
                    Integer.parseInt(tempArray[4]),     // Publication year (int)
                    tempArray[5],                       // Original title (String)
                    tempArray[6],                       // Current title (String)
                    Double.parseDouble(tempArray[7]))); // Average rating (double)
        }

        bookFile.close();

        // Commence demonstrative UI
        System.out.println("-----------------------------");
        System.out.println("Hello and welcome to the library! We currently have " + library.size() +
                           " books in store!");
        System.out.println("-----------------------------");
        System.out.println("Try our new navigation system! Enter the number listed below to...:");
        libraryNavigationHome();
    }

    // Home for UI
    public static void libraryNavigationHome() {
        Scanner scanner = new Scanner(System.in);
        int numberEntered;
        boolean isValid = false;

        while (!isValid) {
            System.out.println("[1] Check if we have a book in store by entering its ISBN");
            System.out.println("[2] Look up a book by its ISBN");
            System.out.println("[3] Exit the library");
            if (scanner.hasNextInt()) {
                numberEntered = scanner.nextInt();
                if (numberEntered > 3 || numberEntered < 1) {
                    System.out.println("Invalid input: Please enter one of the options listed.\n");
                } else {
                    isValid = true;
                    switch (numberEntered) {
                        case 1 -> libraryNavigationIsbnCheck();
                        case 2 -> libraryNavigationLookUp();
                        case 3 -> System.out.println("Goodbye! Have a wonderful day :)");
                    }
                }
            } else {
                System.out.println("Invalid input: Please enter one of the options listed.\n");
                scanner.next();
            }
        }

        scanner.close();
    }

    // UI to check if library contains ISBN using containsKey
    public static void libraryNavigationIsbnCheck() {
        Scanner scanner = new Scanner(System.in);
        String numberEntered;
        boolean isValid = false;

        System.out.println("-----------------------------");
        System.out.println("Welcome to the storage check system!");
        System.out.println("-----------------------------");

        while (!isValid) {
            System.out.println("Enter a 10-digit ISBN number, or 0 to exit back to home: ");
            numberEntered = scanner.next();

            // Try/catch to make sure the String entered is actually a number
            try {
                Long.parseLong(numberEntered);

                // Exit
                if (numberEntered.equals("0")) {
                    System.out.println("Traveling home...");
                    libraryNavigationHome();
                    isValid = true;
                // Detect if length of isbn is valid
                } else if (numberEntered.length() != 10) {
                    System.out.println("Invalid input: " +
                            "Please enter a valid number, with no dashes or spaces.\n");
                // If valid, try to lookup book using containsKey
                } else {
                    boolean checkIsbn = library.containsKey(numberEntered);
                    if (!checkIsbn) {
                        System.out.println("Unfortunately, we do not have book in store.\n");
                    } else {
                        System.out.println("We have this book in store!\n");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " +
                        "Please enter a valid number, with no dashes or spaces.\n");
            }
        }

        scanner.close();
    }

    // UI to look up a certain book and its info through its ISBN, using get
    public static void libraryNavigationLookUp() {
        Scanner scanner = new Scanner(System.in);
        String numberEntered;
        boolean isValid = false;

        System.out.println("-----------------------------");
        System.out.println("Welcome to the lookup system!");
        System.out.println("-----------------------------");

        while (!isValid) {
            System.out.println("To check information about a book, enter a 10-digit ISBN number, " +
                    "or 0 to exit back to home: ");
            numberEntered = scanner.next();

            // Try/catch to make sure the String entered is actually a number
            try {
                Long.parseLong(numberEntered);

                // Exit
                if (numberEntered.equals("0")) {
                    System.out.println("Traveling home...");
                    libraryNavigationHome();
                    isValid = true;
                // Detect if length of isbn is valid
                } else if (numberEntered.length() != 10) {
                    System.out.println("Invalid input: " +
                            "Please enter a valid number, with no dashes or spaces.\n");
                // If valid, try to lookup book using get
                } else {
                    Book book = library.get(numberEntered);
                    if (book == null) {
                        System.out.println("Unfortunately, We do not have book in store.\n");
                    } else {
                        System.out.println("Book found! Here is some information about this book:");
                        System.out.println("Title: " + book.title());
                        System.out.println("Author(s): " + book.authors());
                        System.out.println("Year of publication: " + book.publicationYear());
                        System.out.println("Average rating (out of 5 stars): " + book.averageRating() + "\n");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " +
                        "Please enter a valid number, with no dashes or spaces.\n");
            }
        }

        scanner.close();
    }
}
