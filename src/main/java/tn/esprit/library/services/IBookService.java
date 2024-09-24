package tn.esprit.library.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.BookDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IBookService {
    public List<BookDTO> getAllBooks();

    public Book getBookById(Long id_book);

    public Book addBook(Book book, MultipartFile image) throws IOException;

    public void deleteBook(Long id_book);

    public Book updateBook(Book book) throws IOException;

    public List<BookDTO> searchBooks(String name, String specialty, LocalDate availabilityDate);
}
