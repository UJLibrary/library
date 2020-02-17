package model;

import Db.Identifiable;

import java.time.LocalDateTime;

public class User implements Identifiable {
    public int userId;
    public boolean activeStatus;
    public LocalDateTime registeredTime;
    public String name;
    public String email;
    private String passSalt;
    private String passHash;


    @Override
    public int getId() {
        return userId;
    }

    @Override
    public void setId(int id) {
        userId = id;
    }
}
