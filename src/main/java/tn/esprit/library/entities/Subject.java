package tn.esprit.library.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_subject;

    private String name;

    private int grade;



    @Enumerated(EnumType.STRING)
    private Specialty spec;

    @OneToMany(mappedBy ="subject" )
    @JsonBackReference // Manage the back reference
    private List<Resource> resourceList;

}

