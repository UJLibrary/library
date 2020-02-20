package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import Db.Identifiable;

public class Magazine extends Product{
    public Period period;

    public Magazine(String name, String publisher, LocalDate releaseDate) {
        super(name, publisher, releaseDate);
        productTypeSignature = getProductTypeSignature();
    }

    @Override
    public String getProductTypeSignature() {
        if (productTypeSignature == null && (name == null || publisher == null)) {
            throw new IllegalStateException("Attribute productTypeSignature or name and publisher must be instantiated " +
                    "before calling this method.");
        }

        if(productTypeSignature != null) {
            return productTypeSignature;
        } else {
            productTypeSignature = name + publisher;
            return productTypeSignature;
        }
    }
}
