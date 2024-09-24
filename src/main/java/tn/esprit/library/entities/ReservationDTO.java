package tn.esprit.library.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.library.entities.Status;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id_reservation;
    private Date reservation_date;
    private Date return_date;
    private Status status;
    private UserDTO user;
    private BookDTO book;

    private Long book_id;
}

