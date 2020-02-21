package Tests;

import Controllers.AuthorController;
import Controllers.BookController;
import Db.Db;
import model.Author;
import model.Book;
import model.BookAuthor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {
    private Db db;
    private BookController bc;
    private AuthorController ac;

    @BeforeEach
    void setUp() {
        this.db = new Db();
        this.bc = new BookController(db);
        this.ac =  new AuthorController(db);

        Author author1 = ac.createAuthor("Dan Brown");
        int bookId1 = db.books.add(new Book(
                "Cyfrowa Twierdza", "Wydawnictwo", LocalDate.now(), author1, "11"));
        Author dbAuthor1 = db.authors.getFirstOrNull(e -> e.name.equals(author1.name));
        db.bookAuthors.add(new BookAuthor(bookId1, dbAuthor1.authorId));

        Author author2 = ac.createAuthor("Antoine de Saint-Exupéry");
        int bookId2 = db.books.add(new Book(
                "Twierdza", "Wydawnictwo", LocalDate.now(), author2, "12"));
        Author dbAuthor2 = db.authors.getFirstOrNull(e -> e.name.equals(author2.name));
        db.bookAuthors.add(new BookAuthor(bookId2, dbAuthor2.authorId));

        Author author3 = ac.createAuthor("Henryk Sienkiewicz");
        int bookId3 = db.books.add(new Book(
                "Ogniem i Mieczem", "Wydawnictwo", LocalDate.now(), author3, "13"));
        Author dbAuthor3 = db.authors.getFirstOrNull(e -> e.name.equals(author3.name));
        db.bookAuthors.add(new BookAuthor(bookId3, dbAuthor3.authorId));

        int bookId4 = db.books.add(
                new Book("Ogniem i Mieczem", "Wydawnictwo", LocalDate.now(), author3, "14"));
        db.bookAuthors.add(new BookAuthor(bookId4, dbAuthor3.authorId));
    }

    @Test
    void createBook() {
        Author author = new Author("John Ronald Reuel Tolkien");
        Book book = bc.createBook("Silmarillion", "Wydawnictwo2", LocalDate.now(), author, "4");

        // New author got added to db.
        Assert.assertNotEquals(null, db.authors.getFirstOrNull(e -> e.name == author.name));
        // New book got added to db.
        Assert.assertEquals(book, db.books.get(book.productId));

        Author authorSame = new Author("John Ronald Reuel Tolkien");
        Book bookOther = bc.createBook(
                "Silmarillion", "Wydawnictwo2", LocalDate.now(), author, "5");

        // No copy of author.
        Assert.assertEquals(db.authors.search(e -> e.name.equals(authorSame.name)).size(), 1);

        // Book with the same ISBN as an existent one causes IllegalArgumentException.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> bc.createBook("Silmarillion", "Wydawnictwo2", LocalDate.now(), author, "5"));
        Assert.assertEquals("ISBN must be unique.", thrown.getMessage());
    }

    @Test
    void searchByType() {
        Author author3 = new Author("Henryk Sienkiewicz");
        List<Book> books = bc.searchByType(new Book(
                "Ogniem i Mieczem", "Wydawnictwo", LocalDate.now(), author3, "14"));

        Assert.assertEquals(books.size(), 2);
    }

    @Test
    void searchByAuthor() {
        Author author = db.authors.getFirstOrNull(e -> e.name.equals("Henryk Sienkiewicz"));
        List<Book> books = bc.searchByAuthor(author);
        Assert.assertEquals(books.size(), 2);
    }

    @Test
    void searchByTitle() {
        List<Book> books1 = bc.searchByTitle("twierdza");
        List<Book> books2 = bc.searchByTitle("cyfrowa");
        Assert.assertEquals(books1.size(), 2);
        Assert.assertEquals(books2.size(), 1);
    }
}