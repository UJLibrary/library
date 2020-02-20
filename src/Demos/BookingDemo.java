package Demos;

import Controllers.AuthorController;
import Controllers.BookController;
import Controllers.UserController;
import Db.Db;
import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookingDemo {
    private Db db = new Db();

    public BookingDemo() {
        setup();
    }

    private void setup() {
        var ac = new AuthorController(db);
        var bc = new BookController(db);
        var uc = new UserController(db);
        Author author1 = ac.createAuthor("Dan Brown");
        Book book = bc.createBook("Cyfrowa Twierdza", "Wydawnictwo", LocalDate.now(), author1);
        book.checkOutId = 1;
        Author author2 = ac.createAuthor("Antoine de Saint-Exupéry");
        Book book2 = bc.createBook("Twierdza", "Wydawnictwo", LocalDate.now(), author2);
        book2.checkOutId = 2;
        Author author3 = ac.createAuthor("Henryk Sienkiewicz");
        Book book3 = bc.createBook("Ogniem i Mieczem", "Wydawnictwo", LocalDate.now(), author3);
        book.checkOutId = 3;

        Customer cu1 = new Customer("Thomas K", "email@gmail.com");
        cu1.setPass("haslo123");
        uc.createUser(cu1);
        Customer cu2 = new Customer("Mis Yogi", "123@gmail.com");
        cu2.setPass("haslo321");
        uc.createUser(cu2);
    }

    public void run() {
        var uc = new UserController(db);
        var bc = new BookController(db);
        var ac = new AuthorController(db);
        Scanner scanner = new Scanner(System.in);
        User user;

        System.out.println("\n=== BOOKING DEMO ===\n");

        // Logowanie użytkownika
        while(true) {
            System.out.println("Wpisz adres email: ");
            String email = scanner.nextLine();
            System.out.println("Wpisz haslo: ");
            String pass = scanner.nextLine();

            user = uc.getByEmail(email);
            if (user == null) {
                System.out.println("\nNiepoprawny adres email albo hasło.\n");
                continue;
            }

            if(!uc.authenticate(user, pass)) {
                System.out.println("\nNiepoprawny adres email albo hasło.\n");
                continue;
            }

            break;
        }

        System.out.println("Jesteś zalogowany!\n");

        // Wyszukiwanie ksiazki po tytule
        List<Book> bookList;
        while (true) {
            System.out.println("Wrowadź tytuł lub jego część: ");
            String title = scanner.nextLine();
            bookList = bc.searchByTitle(title);
            if (bookList.size() == 0) {
                System.out.println("Nie znaleziono takiego tytułu.");
                continue;
            }
            break;
        }

        // String ksiazek z autorami
        var sb = new StringBuilder();
        for(int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            List<Author> authors = ac.getAuthorsByBook(book);
            sb.append((i+1) + ": " + book.name + "; " +
                    String.join(", ", authors.stream().map(e -> e.name).collect(Collectors.toList())))
                    .append("\n");
        }

        // Wybor wyszukanych ksiazek
        Book book;
        while (true) {
            System.out.print(sb.toString());
            System.out.println("Wpisz numer pozycji:");
            int num = scanner.nextInt();
            try {
                book = bookList.get(num - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\nNierprawidłowy numer.\n");
                continue;
            }
            break;
        }

        // Jesli ksiazka jest wypozyczona...
        if(book.checkOutId != 0) {
            // ... zapytaj czy ja zarezerwowac
            label: while (true) {
                System.out.println("Książka chwilowo nie jest dostępna.\nCzy zarezerwować książkę (y/n): ");
                switch (scanner.next()) {
                    case "y":
                        var booking = new Booking(
                                user.userId, book.getProductTypeSignature(), 1, 2592000);
                        db.bookings.add(booking);
                        System.out.println("Książka została zarezerwowana.");
                        break label;
                    case "n":
                        break label;
                    default:
                        System.out.println("Nieprawidłowy wybór.");
                        break;
                }
            }
        }

    }
}
