package com.javachinna.repo;

import com.javachinna.model.Contrat;
import com.javachinna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepo extends JpaRepository<Contrat,Long> {

    List<Contrat> findContratByUserId(Long n);
}
