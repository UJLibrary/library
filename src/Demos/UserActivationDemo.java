package Demos;

import Controllers.AuthorController;
import Controllers.BookController;
import Controllers.UserController;
import Db.Db;
import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserActivationDemo {
    private Db db = new Db();

    public UserActivationDemo() {
        setup();
    }

    private void setup() {
        var uc = new UserController(db);

        Customer cu1 = new Customer("Thomas K", "email@gmail.com");
        cu1.setPass("haslo123");
        cu1.activeStatus = true;
        uc.createUser(cu1);
        Customer cu2 = new Customer("Mis Yogi", "123@gmail.com");
        cu2.setPass("haslo321");
        uc.createUser(cu2);
        cu2.activeStatus = false;

        Employee em1 = new Employee("Euzebia", "lody@algida.pl", Role.Staff);
        em1.activeStatus = false;
        uc.createUser(em1);
        Employee em2 = new Employee("Kunegunda", "czekolada@wawel.pl", Role.Staff);
        em1.activeStatus = false;
        uc.createUser(em2);
        Employee admin = new Employee("Przyspawany Marian", "gram@w.pracy", Role.Admin);
        admin.setPass("123");
        admin.activeStatus = true;
        uc.createUser(admin);
    }

    public void run() {
        var uc = new UserController(db);
        Scanner scanner = new Scanner(System.in);
        User user;
        Employee employee;

        System.out.println("\n=== USER ACTIVATION DEMO ===\n");

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

            if(!user.activeStatus){
                System.out.println("To konto jest nieaktywne.\n");
                continue;
            }

            if(!uc.authenticate(user, pass)) {
                System.out.println("\nNiepoprawny adres email albo hasło.\n");
                continue;
            }

            if(!uc.isEmployee(user)) {
                System.out.println("Tylko administrator może wejść w tą sekcję.\n");
                continue;
            }

            employee = (Employee) user;

            if(employee.role != Role.Admin) {
                System.out.println("Tylko administrator może wejść w tą sekcję.\n");
            }

            break;
        }

        // Menu aktywujące użytkowników.
        while (true){
            List<User> nonActive = uc.searchByActiveStatus(false);
            if (nonActive.size() == 0) {
                System.out.println("\nAll users are active.\nPress to quit.");
                scanner.next();
                return;
            }

            var sb = new StringBuilder("\n\n");
            for (int i = 0; i < nonActive.size(); i++) {
                User u = nonActive.get(i);
                sb.append(String.format("%d.\t%s\t%s\t%s\n",
                        i+1, u.name, u.email, uc.isEmployee(u) ? "Emloyee" : "Customer"));
            }

            System.out.println(sb.toString());

            System.out.println("Wpisz numer pozycji:");
            int num = scanner.nextInt();
            try {
                User selected = nonActive.get(num - 1);
                selected.activeStatus = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\nNierprawidłowy numer.\n");
                continue;
            }
        }

    }
}
