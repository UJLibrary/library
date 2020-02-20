package model;
import java.time.LocalDateTime;
import Db.Identifiable;

public class BookAuthor{
    private Book book;
    public BookAuthor() {
        this.book = new Book();
        book.setBookId();
    }
    public int getId() {
        return book.getBookId();
    }
    @Override
    public int getId() {
        return authorId;
    }
    @Override
    public void setId(int id) {
        authorId = id;
    }
}
