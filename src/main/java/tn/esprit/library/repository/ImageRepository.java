package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.library.entities.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
}
