package com.javachinna.controller;

import com.javachinna.model.Reclamation;
import com.javachinna.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;
    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @Valid Reclamation reclamation) {
        try {
            reclamationService.saveReclamation(file, reclamation);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "File uploaded and form data saved successfully");
            return ResponseEntity.ok(response);  // Send JSON response
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Send JSON response
        }
    }
    @GetMapping
    @RequestMapping("/getall")
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        if (reclamations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reclamations);
    }
}
