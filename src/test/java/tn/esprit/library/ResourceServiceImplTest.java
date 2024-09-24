package tn.esprit.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.entities.Resource;
import tn.esprit.library.entities.User;
import tn.esprit.library.repository.IResourceRepository;
import tn.esprit.library.repository.IUserRepository;
import tn.esprit.library.services.ResourceServiceImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceServiceImplTest {

    @Mock
    private IResourceRepository resourceRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllResources() {
        // Arrange
        List<Resource> resources = List.of(new Resource(), new Resource());
        when(resourceRepository.findAll()).thenReturn(resources);

        // Act
        List<Resource> result = resourceService.retrieveAllResources();

        // Assert
        assertEquals(2, result.size());
        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveResource() {
        // Arrange
        Resource resource = new Resource();
        resource.setId_resource(1L);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        // Act
        Resource result = resourceService.retrieveResource(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_resource());
        verify(resourceRepository, times(1)).findById(1L);
    }

    @Test
    void testAddResource() {
        // Arrange
        Resource resource = new Resource();
        when(resourceRepository.save(resource)).thenReturn(resource);

        // Act
        Resource result = resourceService.addResource(resource);

        // Assert
        assertNotNull(result);
        verify(resourceRepository, times(1)).save(resource);
    }

    @Test
    void testRemoveResource() {
        // Arrange
        Long resourceId = 1L;
        doNothing().when(resourceRepository).deleteById(resourceId);

        // Act
        resourceService.removeResource(resourceId);

        // Assert
        verify(resourceRepository, times(1)).deleteById(resourceId);
    }

    @Test
    void testUploadImage() throws IOException {
        // Arrange
        byte[] content = "image content".getBytes();
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getBytes()).thenReturn(content);

        MultipartFile[] files = {multipartFile};

        // Act
        Set<ImageModel> imageModels = resourceService.uploadImage(files);

        // Assert
        assertNotNull(imageModels);
        assertEquals(1, imageModels.size());
        ImageModel imageModel = imageModels.iterator().next();
        assertEquals("image/jpeg", imageModel.getType());
        assertArrayEquals(content, imageModel.getPicByte());
    }

    @Test
    void testAddResourceWithImages() throws IOException {
        // Arrange
        Resource resource = new Resource();
        byte[] content = "image content".getBytes();
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getBytes()).thenReturn(content);

        MultipartFile[] files = {multipartFile};
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        // Act
        Resource result = resourceService.addResourceWithImages(resource, files);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getResourceImages());
        assertEquals(1, result.getResourceImages().size());
        verify(resourceRepository, times(1)).save(resource);
    }

    @Test
    void testFindUserById() {
        // Arrange
        User user = new User();
        user.setId_user(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = resourceService.findUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_user());
        verify(userRepository, times(1)).findById(1L);
    }
}
