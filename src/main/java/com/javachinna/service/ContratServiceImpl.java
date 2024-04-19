package com.javachinna.service;

import com.javachinna.model.Contrat;
import com.javachinna.model.CountType;
import com.javachinna.model.User;
import com.javachinna.repo.ContratRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements ContratService{

    @Autowired
    private ContratRepo contratRepo;
    @Override
    public List<Contrat> getAllContarts() {
        return contratRepo.findAll();
    }

    @Override
    public List<Contrat> findContratByUserId(Long id) {
        return contratRepo.findContratByUserId(id);
    }

    @Override
    public Contrat getContartById(Long id) {
        // TODO Auto-generated method stub
        Optional<Contrat> o= contratRepo.findById(id);
        return o.isPresent() ? o.get() :null;
    }

    @Override
    public void deleteContart(Long id) {
        contratRepo.deleteById(id);
    }

    @Override
    public List<CountType> getPercentageGroupByBranche() {
        return contratRepo.getPercentageGroupByBranche();
    }
}
