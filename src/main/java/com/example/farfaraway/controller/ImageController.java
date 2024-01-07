package com.example.farfaraway.controller;

import com.example.farfaraway.entity.Image;
import com.example.farfaraway.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("/admin/images")
    public ResponseEntity<?> getFilesOfCurrentUser() {
        return ResponseEntity.ok(imageService.getFilesOfCurrentUser());
    }

    @PostMapping("/admin/images")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.uploadFile(file));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> readFile(@PathVariable Integer id) {
        Image image = imageService.getFileById(id);
        // Code logic
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData()); // 200
    }

    @DeleteMapping("/admin/images/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer id) {
        imageService.deleteFile(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
