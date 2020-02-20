package Controllers;

import Db.Db;
import model.Booking;
import model.Customer;
import model.ProductType;

import java.util.Arrays;
import java.util.List;

public class BookingController {
    private Db db;

    public BookingController(Db db) {
        this.db = db;
    }

    public Booking createBooking(Customer customer, ProductType product, int quantity, long maxAwait) {
        if (customer.activeStatus) {
            var booking = new Booking(customer.getId(), product.getProductTypeSignature(), quantity, maxAwait);
            db.bookings.add(booking);
            return booking;
        } else {
            throw new IllegalArgumentException("Customer is not active.");
        }
    }

    public List<Booking> getUserBookings(int userId) {
        return Arrays.asList( db.bookings.search(e -> e.userId == userId).toArray(new Booking[0]));
    }

    public boolean removeBooking(int bookingId) {
        try {
            db.bookings.remove(bookingId);
            return true;
        } catch (IllegalArgumentException e) {
            return  false;
        }
    }
}
