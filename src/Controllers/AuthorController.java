package Controllers;

import Db.Db;
import model.Author;
import model.Book;
import model.BookAuthor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorController {
    private Db db;

    public AuthorController(Db db) {
        this.db = db;
    }

    public Author createAuthor(String name) {
        if (db.authors.getFirstOrNull(e -> e.name == name) != null)
            throw new IllegalArgumentException("Author name already present in database.");
        else {
            var author = new Author(name);
            db.authors.add(author);
            return author;
        }
    }

    public List<Author> getAuthorsByBook(Book book) {
        List<BookAuthor> bookAuthors = db.bookAuthors.search(e -> e.bookId == book.productId);
        var authorIds = bookAuthors.stream().mapToInt(e -> e.authorId).toArray();
        return db.authors.search(e ->
                Arrays.stream(authorIds).anyMatch(el -> el == e.authorId));
    }
}
