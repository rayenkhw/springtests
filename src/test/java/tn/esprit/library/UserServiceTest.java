package tn.esprit.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tn.esprit.library.entities.User;
import tn.esprit.library.repository.IUserRepository;
import tn.esprit.library.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Arrange
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.addUser(user);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {
        // Arrange
        User user = new User();
        user.setId_user(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_user());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUser();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setId_user(1L);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_user());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testCheckProfile() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setMail(email);
        user.setPassword(password);
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        User result = userService.checkProfile(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getMail());
        assertEquals(password, result.getPassword());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSendSimpleMessage() {
        // Arrange
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "Test Email Body";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        doNothing().when(emailSender).send(message);

        // Act
        userService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(emailSender, times(1)).send(message);
    }
}

