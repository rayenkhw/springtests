package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    public User findByMail(String mail);

}
