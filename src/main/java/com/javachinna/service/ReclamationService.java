package com.javachinna.service;

import com.javachinna.model.Reclamation;
import com.javachinna.repo.Reclamationrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class ReclamationService {

    @Autowired
    private Reclamationrepo reclamationRepository;

    @Transactional
    public void saveReclamation(MultipartFile file, Reclamation reclamation) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        reclamation.setFileData(file.getBytes()); // Set the file data
        reclamationRepository.save(reclamation);
    }
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }
}