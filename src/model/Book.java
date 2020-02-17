package model;
import java.time.LocalDateTime;
import Db.Identifiable;

public class Book extends Product{
    public String ISBN;
    public String barcode;
    
    @Override
    public int getId() {
        return bookId;
    }
    @Override
    public void setId(int id) {
        bookId = id;
    }
}
