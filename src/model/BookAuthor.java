package model;
import Db.Identifiable;

public class BookAuthor implements Identifiable{
    public int bookAuthorId;
    public int bookId;
    public int authorId;

    public BookAuthor(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    @Override
    public int getId() {
        return bookAuthorId;
    }
    @Override
    public void setId(int id) {
        bookAuthorId = id;
    }
}
