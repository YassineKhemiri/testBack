package com.javachinna.repo;

import com.javachinna.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProduitRepo extends JpaRepository<Produit,Long> {


}
