package tn.esprit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.services.IImageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") Long id) {
        ImageModel image = imageService.getImageById(id);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(image.getPicByte());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, image.getType());
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(bis));
    }
}
