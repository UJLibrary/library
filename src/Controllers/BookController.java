package Controllers;

import Db.Db;
import model.Author;
import model.Book;
import model.BookAuthor;
import model.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BookController {
    private Db db;

    public BookController(Db db) {
        this.db = db;
    }

    public Book createBook(String name, String publisher, LocalDate releaseDate, Author author) {
        var book = new Book(name, publisher, releaseDate, author);
        try {
            db.authors.add(author);
        } catch (IllegalArgumentException e) { }

        int bookId = db.books.add(book);

        db.bookAuthors.add(new BookAuthor(bookId, author.authorId));

        return book;
    }

    public List<Book> searchByType(ProductType productType) {
        return Arrays.asList(db.books.search(e ->
                e.getProductTypeSignature() == productType.getProductTypeSignature()).toArray(new Book[0]));
    }

    public List<Book> searchByAuthor(Author author) {
        List<BookAuthor> bookAuthors = Arrays.asList( db.bookAuthors.search(e ->
                e.authorId == author.authorId).toArray(new BookAuthor[0]));
        var bookIds = bookAuthors.stream().mapToInt(e -> e.bookId);
        return Arrays.asList( db.books.search(e -> bookIds.anyMatch(el -> el == e.productId)).toArray(new Book[0]));
    }

    public List<Book> searchByTitle(String title) {
        return db.books.search(e ->
                e.name.toLowerCase().contains(title.toLowerCase()));
    }
}
