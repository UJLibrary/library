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
import java.util.stream.Collectors;

public class BookController {
    private Db db;

    public BookController(Db db) {
        this.db = db;
    }

    public Book createBook(String name, String publisher, LocalDate releaseDate, Author author, String isbn) {
        var book = new Book(name, publisher, releaseDate, author, isbn);
        var ac = new AuthorController(db);
        try {
            ac.createAuthor(author.name);
        } catch (IllegalArgumentException e) { }

        if (db.books.search(e -> e.ISBN.equals(isbn)).size() != 0)
            throw new IllegalArgumentException("ISBN must be unique.");

        int bookId = db.books.add(book);

        Author dbAuthor = db.authors.getFirstOrNull(e -> e.name.equals(author.name));

        db.bookAuthors.add(new BookAuthor(bookId, dbAuthor.authorId));

        return book;
    }

    public List<Book> searchByType(ProductType productType) {
        return Arrays.asList(db.books.search(e ->
                e.getProductTypeSignature().equals(productType.getProductTypeSignature())).toArray(new Book[0]));
    }

    public List<Book> searchByAuthor(Author author) {
        List<BookAuthor> bookAuthors = Arrays.asList( db.bookAuthors.search(e ->
                e.authorId == author.authorId).toArray(new BookAuthor[0]));
        var bookIds = bookAuthors.stream().map(e -> e.bookId).collect(Collectors.toList());
        return Arrays.asList( db.books.search(
                e -> bookIds.stream().anyMatch(el -> el.equals(e.productId))).toArray(new Book[0]));
    }

    public List<Book> searchByTitle(String title) {
        return db.books.search(e ->
                e.name.toLowerCase().contains(title.toLowerCase()));
    }
}
