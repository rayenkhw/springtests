package tn.esprit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.library.entities.Notification;
import tn.esprit.library.services.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin
public class NotificationController {

    @Autowired
    INotificationService notificationService;

    @CrossOrigin
    @GetMapping
    public List<Notification> retrieveAllNotifications() {
        return notificationService.retrieveAllNotifications();
    }

    @GetMapping("/{id}")//3 methods path overlap
    public ResponseEntity<Notification> retrieveNotification(@PathVariable("id") Long id_notification) {
        Notification notification = notificationService.retrieveNotification(id_notification);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping//na9es el path
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationService.addNotification(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> modifyNotification(@PathVariable("id") Long id_notification, @RequestBody Notification notification) {
        Notification existingNotification = notificationService.retrieveNotification(id_notification);
        if (existingNotification != null) {
            notification.setId_notification(id_notification);
            Notification updatedNotification = notificationService.modifyNotification(notification);
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeNotification(@PathVariable("id") Long id_notification) {
        Notification notification = notificationService.retrieveNotification(id_notification);
        if (notification != null) {
            notificationService.removeNotification(id_notification);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}