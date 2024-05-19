package com.javachinna.service;

import com.javachinna.model.Produit;
import com.javachinna.repo.ProduitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepo produitRepo;
    @Override
    public List<Produit> getAllProduits() {
        return produitRepo.findAll();
    }

    @Override
    @Transactional
    public void addProduct(MultipartFile icon, MultipartFile image, Produit produit) throws IOException {
        if (image.isEmpty() || icon.isEmpty()) {
            throw new IllegalArgumentException("Icon and image cannot be empty");
        }
        byte[] iconBytes = icon.getBytes();
        byte[] imageBytes = image.getBytes();
        produit.setImage(imageBytes);
        produit.setIcon(iconBytes);
        produitRepo.save(produit);

    }


    @Override
    @Transactional
    public void EditProduct(MultipartFile icon, MultipartFile image, Produit produit) throws IOException{
        if (image.isEmpty() || icon.isEmpty()) {
            throw new IllegalArgumentException("Icon and image cannot be empty");
        }
        byte[] iconBytes = icon.getBytes();
        produit.setIcon(iconBytes);
        produit.setImage(image.getBytes());
        produitRepo.save(produit);

    }

    @Override
    public void deleteProduct(Long Idproduit) {
        produitRepo.deleteById(Idproduit);
    }

    @Override
    public Produit getProduitById(Long id) {
        Optional<Produit> o= produitRepo.findById(id);
        return o.isPresent() ? o.get() :null;
    }
}
