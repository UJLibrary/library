package model;
import java.time.LocalDateTime;
import Db.Identifiable;

abstract class Product implements Identifiable{
    public int productId;
    public String name;
    public String publisher;
    public LocalDate releaseDate;

    @Override
    public int getId() {
        return productId;
    }
    @Override
    public void setId(int id) {
        productId = id;
    }

}

public class Book extends Product{
    public String ISBN;
    public String barcode;
}

class Magazine extends Product{

}
