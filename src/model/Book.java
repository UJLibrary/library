package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Db.Identifiable;

public class Book extends Product{
    public String ISBN;
    public String barcode;
    public ArrayList<Author> authors;

    public Book(String name, String publisher, LocalDate releaseDate, Author author, String isbn) {
        super(name, publisher, releaseDate);
        this.ISBN = isbn;
        authors = new ArrayList<Author>();
        authors.add(author);
        this.productTypeSignature = getProductTypeSignature();
    }


    @Override
    public String getProductTypeSignature() {
        if (productTypeSignature == null && (name == null || authors == null)) {
            throw new IllegalStateException("Attribute productTypeSignature or name and authors must be instantiated " +
                    "before calling this method.");
        }

        if (productTypeSignature != null) {
            return productTypeSignature;
        } else {
            String[] names = authors.stream().map(a -> a.name).toArray(String[]::new);
            productTypeSignature = name + String.join("", names);
            return productTypeSignature;
        }
    }
}
