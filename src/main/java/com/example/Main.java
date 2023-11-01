package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.example.entities.Author;
import com.example.entities.Book;
import com.example.entities.Publisher;
import com.example.services.AuthorService;
import com.example.services.BookService;
import com.example.services.PublisherService;


public class Main {
    static PublisherService ps = new PublisherService();
    static AuthorService as = new AuthorService();
    static BookService bs = new BookService();
    static final int MENU_OPT = 12;
    //static Services so they can be called from a static method to fill the DB with initial values;
    public static void main(String[] args) {
        boolean active = true;
        Scanner scan = new Scanner(System.in);
        bs.setServices(ps, as);
        fillDb();
        System.out.println("Welcome to the Bookshop App.");

        while (active) {
            showMenu();
            int opc = promptForOption(scan);
            String msg;
            switch(opc) {
                case 1:
                    String publisher = promptForName(scan);
                    msg = ps.savePublisher(ps.createPublisher(publisher)) ? "Publisher registered successfully" : "There is already a publisher with that name in our DB.";
                    System.out.println(msg);
                    break;
                case 2:
                    String author = promptForName(scan);
                    msg = as.saveAuthor(as.createAuthor(author)) ? "The author has been registered!" : "There is already an author with that name in our DB.";
                    System.out.println(msg);
                    break;
                case 3: 
                    bs.saveBook(promptForNewBook(scan));
                    break;
                 case 4:
                    author = promptForName(scan);
                    List<Book> books = bs.getBooksByAuthor(author);
                    showBooks(books);
                    break;
                 case 5:
                    publisher = promptForName(scan);
                    books = bs.getBooksByPublisher(publisher);
                    showBooks(books);
                    break;
                 case 6:
                    String title = promptForName(scan);
                    books = bs.getBooksByTitle(title);
                    showBooks(books);
                    break;
                 case 7:
                    long isbn = promptForNumber(scan);
                    Book book = bs.getBookByIsbn(isbn);
                    msg = book == null ? "There are no books with that Isdb in our Database." : book.toString();
                    System.out.println(msg);
                    break;
                 case 8:
                    author = promptForName(scan);
                    Author auth = as.getAuthorByName(author);
                    msg = auth == null ? "There is no author with that name in our Database." : auth.toString();
                    System.out.println(msg);
                    break;
                case 9:
                    showBooks(bs.getBooks());
                    break;
                case 10:
                    List<Author> authors = as.getAuthors();
                    if (authors.size() == 0)
                        System.out.println("There are no authors in our Database.");
                    else 
                        authors.forEach(a -> System.out.println(a));
                    break;
                case 11:
                    List<Publisher> publishers = ps.getPublishers();
                    if (publishers.size() == 0)
                        System.out.println("There are no publishers in our Database.");
                    else
                        publishers.forEach(p -> System.out.println(p));
                    break;
                 case 12:
                    active = false;
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.print("\n1. Register new publisher.");
        System.out.print("\t\t2. Register new author.");
        System.out.print("\n3. Register new book.");
        System.out.print("\t\t\t4. Search books by author.");
        System.out.print("\n5. Search books by publisher.");
        System.out.print("\t\t6. Search book by title.");
        System.out.print("\n7. Search book by Isbn.");
        System.out.print("\t\t\t8. Search author by name.");
        System.out.print("\n9. Show all books.");
        System.out.print("\t\t\t10. Show all authors.");
        System.out.print("\n11. Show all publishers.");
        System.out.print("\t\t12. Exit.");
    }


    public static boolean isNullOrBlank(String string) {
        return (string == null || string.isBlank());
    }

    public static String promptForName(Scanner scan) {
        while (true) {
            System.out.print("Enter a valid name: ");
            String name = scan.nextLine();
            if (isNullOrBlank(name)) continue;
            else return name;
        }
    }

    public static boolean isInvalidNumber(int n) {
        return n < 0;
    }

    public static int promptForNumber(Scanner scan) {
        while (true) {
            System.out.print("Enter a valid number: ");
            if (!scan.hasNextInt()) {
                scan.nextLine(); //To capture incorrect input.
                continue;
            }
            int n = scan.nextInt();
            scan.nextLine(); //Throwaway scan.
            if (!isInvalidNumber(n))
                return n;
        }
    }

    public static int promptForOption(Scanner scan) {
        while (true) {
            System.out.print("\nSelect an option: ");
            if(!scan.hasNextInt()) {
                scan.nextLine(); //To capture incorrect input.
                continue;
            }
            int n = scan.nextInt();
            scan.nextLine(); //Throwaway scan.
            if (!isInvalidMenuOpt(n))
                return n;
        }
    }

    public static boolean isInvalidMenuOpt(int n) {
        return n < 1 || n > MENU_OPT;
    }

    public static boolean isInvalidYear(int year) {
        return year > LocalDate.now().getYear();
    }

    public static int promptForYear(Scanner scan) {
        while (true) {
            System.out.print("Enter a valid year: ");
            if (!scan.hasNextInt()) {
                scan.nextLine(); //To capture incorrect input.
                continue;
            }
            int year = scan.nextInt();
            scan.nextLine(); //Throwaway scan.
            if (!isInvalidYear(year))
                return year;
        }
    }

    public static Book promptForNewBook(Scanner scan) {
        System.out.print("\nTitle - ");
        String title = promptForName(scan);
        int stock = promptForNumber(scan);
        int year = promptForYear(scan);
        System.out.print("\nAuthor - ");
        String authorsName = promptForName(scan);
        System.out.print("\nPublisher - ");
        String publishersName = promptForName(scan);

        Author auth = as.getAuthorByName(authorsName);
        auth = auth == null ? as.createAuthor(authorsName) : auth;
        
        Publisher pub = ps.getPublisherByName(publishersName);
        pub = pub == null ? ps.createPublisher(publishersName) : pub;
        

        return bs.createBook(title, year, stock, auth, pub);
    }

    public static void showBooks(List<Book> books) {
        if (books.size() == 0)
            System.out.println("There are no books meeting the criteria");
        else 
            books.forEach(b -> System.out.println(b));
    }

    public static void fillDb() {
        bs.saveBook(bs.createBook("Harry Potter", 1998, 500, new Author("J.K. Rowling"), new Publisher("Salamanca")));
        bs.saveBook(bs.createBook("El señor de los anillos", 1998,500, new Author("J.R.R Tolkien"), new Publisher("Minotauro")));
    }

}