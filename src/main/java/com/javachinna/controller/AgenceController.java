package com.javachinna.controller;

import com.javachinna.model.Agence;
import com.javachinna.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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


}
