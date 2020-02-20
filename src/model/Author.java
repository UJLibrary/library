package model;

import Db.Identifiable;

public class Author implements Identifiable {
    public int authorId;
    public String name;

    public int getId() {
        return authorId;
    }

    public Author(String name) {
        this.name = name;
    }

    public void setId(int authorId) {
        this.authorId = authorId;
    }
}
