package Db;

import model.*;

public class Db {
    public DbCollection<User> users = new DbCollection<User>();
    public DbCollection<Author> authors = new DbCollection<Author>();
    public DbCollection<Book> books = new DbCollection<Book>();
    public DbCollection<BookAuthor> bookAuthors = new DbCollection<BookAuthor>();
    public DbCollection<Booking> bookings = new DbCollection<Booking>();
    public DbCollection<CheckOut> checkOuts = new DbCollection<CheckOut>();
    public DbCollection<Magazine> magazines = new DbCollection<Magazine>();
}
