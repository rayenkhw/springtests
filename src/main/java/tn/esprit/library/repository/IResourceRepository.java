package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.Resource;

@Repository
public interface IResourceRepository extends JpaRepository<Resource,Long> {

}