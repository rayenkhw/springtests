package tn.esprit.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_resource;

    private String title;


  //  @Enumerated(EnumType.STRING)
 //   private Specialty specialty;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;;

    @ManyToOne
    private User upload;

    @ManyToOne
    private User approve;

    @ManyToOne
    private Subject subject;


    @OneToMany(mappedBy = "resource")
    private List<Document> documents;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "resource_images",
            joinColumns = {
                    @JoinColumn(name = "id_resource")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_image")
            }
    )
    private Set<ImageModel> resourceImages;

}

