package tn.esprit.library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.library.entities.Notification;
import tn.esprit.library.entities.User;
import tn.esprit.library.repository.INotificationRepository;
import tn.esprit.library.repository.IUserRepository;
import tn.esprit.library.services.NotificationServiceImpl;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private INotificationRepository notificationRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNotification_UserExists() {
        // Arrange
        User user = new User();
        user.setId_user(1L);

        Notification notification = new Notification();
        notification.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Act
        Notification savedNotification = notificationService.addNotification(notification);

        // Assert
        assertNotNull(savedNotification);
        assertEquals(user, savedNotification.getUser());
        assertNotNull(savedNotification.getDate()); // Check if date is set
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testAddNotification_UserNotFound() {
        // Arrange
        User user = new User();
        user.setId_user(1L);

        Notification notification = new Notification();
        notification.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> notificationService.addNotification(notification));
        verify(notificationRepository, never()).save(notification);
    }

    @Test
    void testRetrieveNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setId_notification(1L);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Act
        Notification retrievedNotification = notificationService.retrieveNotification(1L);

        // Assert
        assertNotNull(retrievedNotification);
        assertEquals(1L, retrievedNotification.getId_notification());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveNotification() {
        // Arrange
        Long notificationId = 1L;
        doNothing().when(notificationRepository).deleteById(notificationId);

        // Act
        notificationService.removeNotification(notificationId);

        // Assert
        verify(notificationRepository, times(1)).deleteById(notificationId);
    }
}
