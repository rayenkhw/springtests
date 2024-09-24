package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.ImageModel;

@Repository
public interface IImageModelRepository extends JpaRepository<ImageModel,Long> {
}
