package tn.esprit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.library.entities.Book;
import tn.esprit.library.entities.Reservation;
import tn.esprit.library.entities.ReservationDTO;
import tn.esprit.library.services.IBookService;
import tn.esprit.library.services.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin
public class ReservationController {
    @Autowired
    IReservationService reservationService;

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }

    @GetMapping("/getall")
    public List<ReservationDTO> getAllReservation(){return reservationService.getAllReservations(); }

    @GetMapping("/getByBook/{idb}")
    public List<Reservation> getReservationsByBook(@PathVariable("idb")Long id_book){return reservationService.getReservationsByBook(id_book); }

    @GetMapping("/getByUser/{idu}")
    public List<Reservation> getReservationsByUser(@PathVariable("idu")Long id_user){return reservationService.getReservationsByUser(id_user); }

    @GetMapping("/get/{idr}")
    public Reservation getReservation(@PathVariable("idr")Long id_reservation){return reservationService.getReservationById(id_reservation); }

    @DeleteMapping("/delete/{idr}")
    public  void deleteReservation(@PathVariable("idr")Long id_reservation){ reservationService.deleteReservation(id_reservation); }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);

    }

    //na9sa modify reservation
}
