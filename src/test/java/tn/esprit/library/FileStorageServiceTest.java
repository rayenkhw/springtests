package tn.esprit.library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.services.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    private final String uploadDir = "/tmp"; // Temporary upload directory for testing

    @BeforeEach
    void setUp() {
        fileStorageService = new FileStorageService(uploadDir);
    }

    @Test
    void testStoreFile() throws IOException {
        // Create a mock MultipartFile
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Hello, World!".getBytes()
        );

        // Call the method to test
        String storedFileName = fileStorageService.storeFile(mockFile);

        // Validate the result
        assertEquals(StringUtils.cleanPath(mockFile.getOriginalFilename()), storedFileName);

        // Check if the file was created in the target directory
        Path targetPath = Paths.get(uploadDir).resolve(storedFileName);
        assertTrue(Files.exists(targetPath));

        // Clean up after the test
        Files.deleteIfExists(targetPath);
    }

    @Test
    void testStoreFileWithInvalidFilename() {
        // Create a mock MultipartFile with an invalid filename
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "../invalid.txt",
                "text/plain",
                "Hello, World!".getBytes()
        );

        // Test that an exception is thrown
        assertThrows(RuntimeException.class, () -> fileStorageService.storeFile(mockFile));
    }
}
