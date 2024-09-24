package tn.esprit.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.BookDTO;
import tn.esprit.library.entities.Status;
import tn.esprit.library.repository.IBookRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    @Autowired
    IBookRepository bookRepository;

    private static final String UPLOAD_DIR = "/home/aziz/aa"; // Specify your upload directory


    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> {
                    try {
                        return new BookDTO(book, book.getBase64Image());
                    } catch (IOException e) {
                        // Handle exception
                        return new BookDTO(book, null);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Book getBookById(Long id_book) {
        Optional<Book> book = bookRepository.findById(id_book);
        return book.orElse(null); // or throw an exception if you prefer
    }

    @Override
    public Book addBook(Book book,MultipartFile image) throws IOException{
        String imageUrl = saveImage(image);
        book.setImageUrl(imageUrl);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id_book) {
        bookRepository.deleteById(id_book);
    }
    @Override
    public Book updateBook(Book book) throws IOException {
        String base64Image = book.getImageUrl();
        // Handle the base64 image if it's not null or empty
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                // Decode the base64 image and save it
                String imagePath = saveBase64Image(base64Image);
                // Set the image path to the book entity
                book.setImageUrl(imagePath);
            } catch (IOException e) {
                // Handle exception (e.g., log the error, throw a custom exception)
                e.printStackTrace();
            }
        } else {
            // If no base64 image is provided, retain the existing image URL
            String existingImagePath = this.getBookById(book.getId_book()).getImageUrl();
            book.setImageUrl(existingImagePath);
        }
        return bookRepository.save(book);
    }

    private String saveBase64Image(String base64Image) throws IOException {
        if (base64Image == null || base64Image.isEmpty()) {
            return null; // or throw an exception if you prefer
        }

        // Remove data URL scheme if present (e.g., "data:image/png;base64,")
        String base64Data = base64Image;
        if (base64Image.startsWith("data:image")) {
            // Extract the base64 part (after the comma)
            base64Data = base64Image.split(",")[1];
        }

        // Decode the base64 string
        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            // Handle invalid base64 string
            throw new IllegalArgumentException("Invalid base64 image data", e);
        }

        // Generate a unique filename or use a consistent naming scheme
        String fileName = "image_" + System.currentTimeMillis() + ".png";
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Save the file
        Files.write(filePath, imageBytes);

        // Return the URL or path to the saved image
        return filePath.toString(); // Or return a URL based on your application's requirements
    }



    private String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null; // or throw an exception if you prefer
        }

        String fileName = image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Save the file
        Files.write(filePath, image.getBytes());

        // Return the URL to the saved image
        return filePath.toString(); // Or return a URL based on your application's requirements
    }

    public List<BookDTO> searchBooks(String name, String specialty, LocalDate availabilityDate) {
        List<BookDTO> books = this.getAllBooks();
        System.out.println("i am here ------------##########################################");

        // Filter by book name
        if (name != null && !name.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by specialty
        if (specialty != null && !specialty.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getSpecialty().toString().toLowerCase().contains(specialty.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by availability date
        if (availabilityDate != null) {
            books = books.stream()
                    .filter(book -> isBookAvailableOnDate(book, availabilityDate))
                    .collect(Collectors.toList());
        }

        return books;
    }

    private boolean isBookAvailableOnDate(BookDTO book, LocalDate date) {

        Book b = this.getBookById(book.getId_book());
        return b.getReservations().stream()
                .filter(reservation -> reservation.getStatus() == Status.APPROVED)
                .allMatch(reservation ->convertToLocalDateViaInstant(reservation.getReturn_date()).isBefore(date)
                );
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
