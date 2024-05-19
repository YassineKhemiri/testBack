package com.javachinna.service;

import com.javachinna.model.Agence;
import com.javachinna.repo.Agencerepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {
    @Autowired
    private Agencerepo repo;
    public List<Agence> findgov(String governorate) {
        return repo.findByGovernorate(governorate);
    }
    public List<String> findDistinctGovernorates() {
        return repo.findDistinctGovernorates();
    }

    public Optional<Agence> findNearest(double latitude, double longitude) {
        return Optional.ofNullable(repo.findNearest(latitude, longitude));
    }
    public List<Agence> findByCite(String cite) {
        return repo.findByCite(cite);
    }
}
