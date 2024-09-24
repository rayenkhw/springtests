package tn.esprit.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_book;

    private String name;

    private String author;

    private String sujet;

    private String year;

    private Specialty specialty;

    private boolean available;


    @OneToMany(mappedBy="book")
    private List<Reservation> reservations;
    private String imageUrl; // New field for the image URL





    public String getName() {
        return name;
    }

    public Long getId_book() {
        return id_book;
    }

    public String getAuthor() {
        return author;
    }

    public String getSujet() {
        return sujet;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public boolean isAvailable() {
        return available;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public String getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBase64Image() throws IOException {
        if (imageUrl == null) {
            return null;
        }
        File imgFile = new File(imageUrl);
        byte[] fileContent = Files.readAllBytes(imgFile.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

}
