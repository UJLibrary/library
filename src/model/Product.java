package model;
import java.time.LocalDate;
import Db.Identifiable;

public abstract class Product implements Identifiable, ProductType{
    public int productId;
    public int checkOutId;
    public String name;
    public String publisher;
    public LocalDate releaseDate;
    public String productTypeSignature;

    public Product(String name, String publisher, LocalDate releaseDate) {
        this.name = name;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
    }

    @Override
    public int getId() {
        return productId;
    }
    @Override
    public void setId(int id) {
        productId = id;
    }
}
