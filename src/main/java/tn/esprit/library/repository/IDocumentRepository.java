package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.Document;

@Repository
public interface IDocumentRepository extends JpaRepository<Document,Long> {
}
