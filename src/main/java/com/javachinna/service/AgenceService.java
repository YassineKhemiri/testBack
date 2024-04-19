package com.javachinna.service;

import com.javachinna.model.Agence;
import com.javachinna.repo.Agencerepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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



}
