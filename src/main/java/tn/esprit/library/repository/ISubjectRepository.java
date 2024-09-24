package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.Subject;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject,Long> {
}
