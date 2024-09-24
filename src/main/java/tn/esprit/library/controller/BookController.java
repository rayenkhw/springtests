package tn.esprit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.BookDTO;
import tn.esprit.library.entities.Resource;
import tn.esprit.library.services.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin
public class BookController {
    @Autowired
    IBookService bookService;
    @PostMapping("/add")
    public Book addBook(@RequestPart("book") Book book, @RequestPart(value = "image") MultipartFile image) throws IOException {
        return bookService.addBook(book, image);
    }
    @GetMapping("/getall")
    public List<BookDTO> getAllBooks(){return bookService.getAllBooks(); }

    @GetMapping("/getbook/{idr}")
    public Book getBookById(@PathVariable("idr")Long id_book){return bookService.getBookById(id_book); }

    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book) throws IOException {
        System.out.println("Received Book object: " + book);

        return bookService.updateBook(book);
    }
    @GetMapping("/search")
    public List<BookDTO> searchBooks(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String speciality,
            @RequestParam(required = false) String availabilityDate) {

        LocalDate date = null;
        if (availabilityDate != null && !availabilityDate.isEmpty()) {
            System.out.println(availabilityDate);
            date = LocalDate.parse(availabilityDate, DateTimeFormatter.ISO_DATE);
        }
        System.out.println(bookName);
        System.out.println(speciality);
        System.out.println(date);

        return bookService.searchBooks(bookName, speciality, date);
    }

    @DeleteMapping("/delete/{idr}")
    public  void deleteBook(@PathVariable("idr")Long id_book){ bookService.deleteBook(id_book); }
}
