package tn.esprit.library.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.library.entities.Specialty;
import tn.esprit.library.entities.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id_user;
    private String mail;
    private String firstname;
    private String lastname;
    private int priority;
    private Specialty specialty;
    private Type type;
    private String image;
}
