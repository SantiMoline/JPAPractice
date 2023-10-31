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
    //static Services so they can be called from a static method to fill the DB with initial values;
    public static void main(String[] args) {
        boolean active = true;
        Scanner scan = new Scanner(System.in);
        fillDb();
        System.out.println("Welcome to the Bookshop App.");

        while (active) {
            showMenu();
            int opc = promptForOption(scan);
            switch(opc) {
                case 1:
                    String publisher = promptForName(scan);
                    ps.savePublisher(ps.createPublisher(publisher));
                    break;
                case 2:
                    String author = promptForName(scan);
                    as.saveAuthor(as.createAuthor(author));
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
                    long isbn = promptForNumber(scan);
                    Book book = bs.getBookByIsbn(isbn);
                    String msg = book == null ? "There are no books with that Isdb in our Database." : book.toString();
                    System.out.println(msg);
                    break;
                 case 7:
                    author = promptForName(scan);
                    Author auth = as.getAuthorByName(author);
                    msg = auth == null ? "There is no author with that name in our Database." : auth.toString();
                    System.out.println(msg);
                    break;
                 case 8:
                    publisher = promptForName(scan);
                    ps.savePublisher(ps.createPublisher(publisher));
                    break;
                 case 9:
                    active = false;
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println("1. Register new publisher.");
        System.out.println("2. Register new author.");
        System.out.println("3. Register new book.");
        System.out.println("4. Search books by author.");
        System.out.println("5. Search books by publisher.");
        System.out.println("6. Search book by Isbn.");
        System.out.println("7. Search author by name.");
        System.out.println("8. Register new Publisher");
        System.out.println("9. Exit.");
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
            System.out.print("Select an option: ");
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
        return n < 1 || n > 9;
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
            if (!isInvalidYear(year))
                return year;
        }
    }

    public static Book promptForNewBook(Scanner scan) {
        String title = promptForName(scan);
        int stock = promptForNumber(scan);
        int year = promptForYear(scan);
        String authorsName = promptForName(scan);
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