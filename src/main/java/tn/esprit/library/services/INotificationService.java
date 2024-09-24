package tn.esprit.library.services;

import tn.esprit.library.entities.Notification;

import java.util.List;

public interface INotificationService {

    List<Notification> retrieveAllNotifications();

    Notification addNotification(Notification notification);

    Notification retrieveNotification(Long id_notification);

    Notification modifyNotification(Notification notification);

    void removeNotification(Long id_notification);
}
