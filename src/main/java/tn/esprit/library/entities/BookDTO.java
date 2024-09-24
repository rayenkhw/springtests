package tn.esprit.library.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {
    private Long id_book;
    private String name;
    private String author;
    private String sujet;
    private String year;
    private Specialty specialty;
    private boolean available;
    private String base64Image;  // Add this line

    // Constructor
    public BookDTO(Long id_book, String name, String author, String sujet, String year, Specialty specialty, boolean available, String base64Image) {
        this.id_book = id_book;
        this.name = name;
        this.author = author;
        this.sujet = sujet;
        this.year = year;
        this.specialty = specialty;
        this.available = available;
        this.base64Image = base64Image;  // Add this line
    }

    public BookDTO(Book book, String base64Image) {
        this.id_book = book.getId_book();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.sujet = book.getSujet();
        this.year = book.getYear();
        this.specialty = book.getSpecialty();
        this.available = book.isAvailable();
        this.base64Image = base64Image;  // Add this line
    }
}
