package com.javachinna.service;

import com.javachinna.model.Contrat;
import com.javachinna.model.User;

import java.util.List;

public interface ContratService {

    public List<Contrat> getAllContarts();

    public List<Contrat> findContratByUserId(Long id);


    public Contrat getContartById(Long id);

    public void deleteContart(Long id);
}
