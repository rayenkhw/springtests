package tn.esprit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.library.entities.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification,Long> {

}
