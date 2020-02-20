package model;

import Db.Identifiable;
import Helpers.Passwords;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

public abstract class User implements Identifiable {
    public int userId;
    public boolean activeStatus;
    public LocalDateTime registeredTime;
    public String name;
    public String email;
    private byte[] passSalt;
    private byte[] passHash;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.registeredTime = LocalDateTime.now();
    }

    public void setPass(String password) {
        this.passSalt = Passwords.getNextSalt();
        this.passHash = Passwords.hash(password.toCharArray(), this.passSalt);
    }

    public boolean authenticates(String password) {
        var passHash = Passwords.hash(password.toCharArray(), this.passSalt);
        return Passwords.isExpectedPassword(password.toCharArray(), this.passSalt, this.passHash);
    }

    @Override
    public int getId() {
        return userId;
    }

    @Override
    public void setId(int id) {
        userId = id;
    }
}
