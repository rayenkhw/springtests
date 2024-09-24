package tn.esprit.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.library.entities.Notification;
import tn.esprit.library.entities.User;
import tn.esprit.library.repository.INotificationRepository;
import tn.esprit.library.repository.IUserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public List<Notification> retrieveAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification addNotification(Notification notification) {
        // Set the current date and time
        notification.setDate(new Date());

        // Ensure the user exists in the database
        Optional<User> userOptional = userRepository.findById(notification.getUser().getId_user());
        if (userOptional.isPresent()) {
            notification.setUser(userOptional.get());
            return notificationRepository.save(notification);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public Notification retrieveNotification(Long id_notification) {
        return notificationRepository.findById(id_notification).orElse(null);
    }

    @Override
    public Notification modifyNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void removeNotification(Long id_notification) {
        notificationRepository.deleteById(id_notification);
    }

}