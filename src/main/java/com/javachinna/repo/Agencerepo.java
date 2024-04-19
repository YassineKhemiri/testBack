package com.javachinna.repo;

import com.javachinna.model.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Agencerepo extends JpaRepository<Agence,Long> {
    List<Agence> findByGovernorate(String governate);
    @Query("SELECT DISTINCT a.governorate FROM Agence a")
    List<String> findDistinctGovernorates();
}
