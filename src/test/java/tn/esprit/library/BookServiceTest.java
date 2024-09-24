package tn.esprit.library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.BookDTO;
import tn.esprit.library.repository.IBookRepository;
import tn.esprit.library.services.BookService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private IBookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ReturnsBookList() {
        // Arrange
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<BookDTO> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ReturnsBook_WhenExists() {
        // Arrange
        Book book = new Book();
        book.setId_book(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_book());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ReturnsNull_WhenNotExists() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertNull(result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void addBook_SavesBookWithImage() throws IOException {
        // Arrange
        Book book = new Book();
        MultipartFile mockImage = mock(MultipartFile.class);
        when(mockImage.getOriginalFilename()).thenReturn("test.jpg");
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.addBook(book, mockImage);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }
}

