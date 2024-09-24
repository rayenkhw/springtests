package tn.esprit.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.*;
import tn.esprit.library.services.INotificationService;
import tn.esprit.library.services.IResourceService;
import tn.esprit.library.services.ISubjectService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/resource")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin
public class ResourceController {
    @Autowired
    IResourceService resourceService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private ISubjectService subjectService;

    @PostMapping(value = "/add/{ids}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> createResource(
            @RequestPart("resource") String resourceJson,
            @RequestPart("imageFile") MultipartFile[] imageFiles,
            @RequestParam("userId") Long userId,
            @PathVariable("ids") Long id_subject) {

        ObjectMapper mapper = new ObjectMapper();
        Resource resource;
        try {
            resource = mapper.readValue(resourceJson, Resource.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON data for resource.");
        }

        // Retrieve user by ID
        User user = resourceService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user ID.");
        }

        // Set the uploader of the resource
        resource.setUpload(user);

        // Affectation subject resource (using the ids in the path)
        Subject subject = subjectService.retrieveSubject(id_subject);
        List<Resource> lr = subject.getResourceList();
        lr.add(resource);
        subject.setResourceList(lr);
        subjectService.modifySubject(subject);
        resource.setSubject(subject);

        Resource savedResource;
        try {
            savedResource = resourceService.addResourceWithImages(resource, imageFiles);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving files.");
        }

        // Check if the user is a student and create a notification
        if (user.getType() == Type.STUDENT) {
            Notification notification = new Notification();
            notification.setType("Resource Added");
            notification.setMessage("A new resource has been added.");
            notification.setDate(new Date());
            notification.setUser(user);

            notificationService.addNotification(notification);
        }

        return ResponseEntity.ok(savedResource);
    }

    @GetMapping("/getall")
    public List<Resource> retrieveAllResources() {
        return resourceService.retrieveAllResources();
    }

    @GetMapping("/getresource/{idr}")
    public Resource retrieveResource(@PathVariable("idr") Long id_resource) {
        return resourceService.retrieveResource(id_resource);
    }

    @PutMapping("/modify")
    public ResponseEntity<Resource> modifyResource(@RequestBody Resource r) {
        try {
            if (r == null || r.getId_resource() == null) {
                return ResponseEntity.badRequest().build();
            }
            Resource updatedResource = resourceService.modifyResource(r);
            return ResponseEntity.ok(updatedResource);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{idr}")
    public void removeResource(@PathVariable("idr") Long id_resource) {
        resourceService.removeResource(id_resource);
    }


}