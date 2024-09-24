package tn.esprit.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.library.entities.*;

import tn.esprit.library.repository.IReservationRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService{
    @Autowired
    IReservationRepository reservationRepository;

    private NotificationServiceImpl notificationService;
    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        UserDTO userDTO = new UserDTO(
                reservation.getUser().getId_user(),
                reservation.getUser().getMail(),
                reservation.getUser().getFirstname(),
                reservation.getUser().getLastname(),
                reservation.getUser().getPriority(),
                reservation.getUser().getSpecialty(),
                reservation.getUser().getType(),
                reservation.getUser().getImage()
        );
        BookDTO bookDTO = null;
        try {
            bookDTO = new BookDTO(
                    reservation.getBook().getId_book(),
                    reservation.getBook().getName(),
                    reservation.getBook().getAuthor(),
                    reservation.getBook().getSujet(),
                    reservation.getBook().getYear(),
                    reservation.getBook().getSpecialty(),
                    reservation.getBook().isAvailable(),
                    reservation.getBook().getBase64Image() // This might throw IOException
            );
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, possibly setting a default value or returning null
        }
        return new ReservationDTO(
                reservation.getId_reservation(),
                reservation.getReservation_date(),
                reservation.getReturn_date(),
                reservation.getStatus(),
                userDTO,
                bookDTO,
                reservation.getBook().getId_book()
        );
    }

   @Override
    public List<Reservation> getReservationsByUser(Long id_user) {

        //return reservationRepository.findByUser(id_user);

       return List.of();
   }

    @Override
    public List<Reservation> getReservationsByBook(Long id_book) {
        //return reservationRepository.findByBook(id_book);
        return List.of();
    }

    @Override
    public Reservation getReservationById(Long id_reservation) {
        Optional<Reservation> reservation = reservationRepository.findById(id_reservation);
        return reservation.orElse(null);    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id_reservation) {
        reservationRepository.deleteById(id_reservation);

    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void checkAndUpdateReservations() {
        Date currentDate = new Date();
        List<Reservation> reservations = reservationRepository.findAllByStatus(Status.APPROVED);

        for (Reservation reservation : reservations) {
            if (reservation.getReturn_date().before(currentDate)) {
                reservation.setStatus(Status.LATE);
                Notification notification = new Notification();
                notification.setType("Late Book return");
                notification.setMessage("You have to return the book");
                notification.setDate(new Date());
                notification.setUser(reservation.getUser());

                this.notificationService.addNotification(notification);
                reservationRepository.save(reservation);
            }
        }
    }
}
