package com.javachinna.controller;

import com.javachinna.model.Agence;
import com.javachinna.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/agencies")
public class AgenceController {
    @Autowired
    private AgenceService agenceService;
    @GetMapping("/governorates")
    public List<String> getGovernorates() {
        return agenceService.findDistinctGovernorates();
    }

    @GetMapping("/byGovernorate")
    public List<Agence> getAgenciesByGovernorate(@RequestParam String governorate) {
        return agenceService.findgov(governorate);
    }
    @GetMapping("/byCite")
    public List<Agence> getAgenciesByCite(@RequestParam String cite) {
        return agenceService.findByCite(cite);
    }
    @GetMapping("/nearest")
    public ResponseEntity<Agence> getNearestAgence(@RequestParam double lat, @RequestParam double lon) {
        return agenceService.findNearest(lat, lon)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
