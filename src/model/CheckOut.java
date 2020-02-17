package model;
import java.time.LocalDateTime;
import Db.Identifiable;

public class CheckOut implements Identifiable {
    public int checkOutId;
    public int checkOutCustomer;
    public int productId;
    public LocalDateTime checkOutTime;
    public long checkOutPeriod;

    @Override
    public int getId() {
        return productId;
    }

    @Override
    public void setId(int id) {
        productId = id;
    }



}
