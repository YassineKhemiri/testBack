package com.javachinna.repo;

import com.javachinna.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reclamationrepo extends JpaRepository<Reclamation, Long> {

}
