package tn.esprit.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
        StringBuilder message = new StringBuilder();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // skip empty files
            }

            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                message.append("You successfully uploaded '").append(file.getOriginalFilename()).append("'; ");
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Failed to upload '" + file.getOriginalFilename() + "': " + e.getMessage());
            }
        }

        redirectAttributes.addFlashAttribute("message", message.toString());
        return ResponseEntity.ok("Uploaded files successfully: " + message);
    }
}
