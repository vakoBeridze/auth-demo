package ge.vako.library.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final List<String> books;

    public BookController() {
        this.books = new ArrayList<>();
        this.books.add("Effective Java");
        this.books.add("Java: The Complete Reference");
        this.books.add("Modern Java in Action");
        this.books.add("Spring in Action");
        this.books.add("Pro Spring 5");
    }

    @PreAuthorize("hasAnyRole('LIBRARY_ADMIN','TEACHER','STUDENT')")
    @GetMapping
    public List<String> getAllBooks() {
        return books;
    }

    @PreAuthorize("hasAnyRole('LIBRARY_ADMIN','TEACHER')")
    @PostMapping("/{title}")
    public String addBook(@PathVariable String title) {
        books.add(title);
        return title;
    }

    @Secured("LIBRARY_ADMIN")
    @DeleteMapping("/{title}")
    public String deleteBook(@PathVariable String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equalsIgnoreCase(title)) {
                String removedTitle = books.get(i);
                books.remove(i);
                return removedTitle;
            }
        }
        return null;
    }
}
