package tn.esprit.library.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.Reservation;
import tn.esprit.library.entities.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface IReservationService {

    public List<ReservationDTO> getAllReservations();

    public List<Reservation> getReservationsByUser(Long id_user);

    public List<Reservation> getReservationsByBook(Long id_book);

    public Reservation getReservationById(Long id_reservation);

    public Reservation addReservation(Reservation reservation);

    public Reservation updateReservation(Reservation reservation);

    public void deleteReservation(Long id_reservation);
}
