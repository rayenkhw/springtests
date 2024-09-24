package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
}
