package model;

import Db.Identifiable;

import java.time.LocalDateTime;

public class Booking implements Identifiable {
    public int bookingId;
    public int userId;
    public String productType;
    public int quantity;
    public LocalDateTime createdAt;
    public BookingStatus status;
    public long maxAwait;
    public LocalDateTime arrivalTime;

    public Booking(int userId, String productType, int quantity, long maxAwait) {
        this.userId = userId;
        this.productType = productType;
        this.quantity = quantity;
        this.maxAwait = maxAwait;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public int getId() {
        return bookingId;
    }

    @Override
    public void setId(int id) {
        bookingId = id;
    }
}
