package tn.esprit.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.library.entities.*;
import tn.esprit.library.repository.IReservationRepository;
import tn.esprit.library.services.NotificationServiceImpl;
import tn.esprit.library.services.ReservationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private NotificationServiceImpl notificationService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAndUpdateReservations() {
        // Arrange
        Date currentDate = new Date();
        User user = new User();
        user.setId_user(1L);

        Reservation reservation = new Reservation();
        reservation.setId_reservation(1L);
        reservation.setUser(user);
        reservation.setStatus(Status.APPROVED);
        reservation.setReturn_date(new Date(currentDate.getTime() - 1000 * 60 * 60 * 24)); // Set return date in the past

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findAllByStatus(Status.APPROVED)).thenReturn(reservations);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        reservationService.checkAndUpdateReservations();

        // Assert
        assertEquals(Status.LATE, reservation.getStatus()); // Verify status changed to LATE
        verify(notificationService, times(1)).addNotification(any(Notification.class));
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testGetReservationById() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setId_reservation(1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.getReservationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_reservation());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testAddReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation savedReservation = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(savedReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testDeleteReservation() {
        // Arrange
        Long reservationId = 1L;
        doNothing().when(reservationRepository).deleteById(reservationId);

        // Act
        reservationService.deleteReservation(reservationId);

        // Assert
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }
}
