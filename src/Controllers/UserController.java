package Controllers;

import Db.Db;
import model.Book;
import model.Customer;
import model.Employee;
import model.User;

import java.util.List;
import java.util.Optional;

public class UserController {
    private Db db;

    public UserController(Db db) {
        this.db = db;
    }

    public User createUser(User user) {
        if (db.users.getFirstOrNull(e -> e.email.equals(user.email)) != null) {
            throw new IllegalArgumentException("The email is already used.");
        }

        try {
            db.users.add(user);
            return user;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public User getByEmail(String email) {
        return db.users.getFirstOrNull(e -> e.email.equals(email));
    }

    public List<User> searchByActiveStatus(boolean status) {
        return db.users.search(e -> e.activeStatus == status);
    }

    public boolean authenticate(User user, String password) {
        return user.authenticates(password);
    }

    public boolean isCustomer(User user) {
        try {
            var cu =(Customer) user;
            return true;
        }   catch (ClassCastException e) {
            return false;
        }
    }

    public boolean isEmployee(User user) {
        try {
            var cu =(Employee) user;
            return true;
        }   catch (ClassCastException e) {
            return false;
        }
    }
}
