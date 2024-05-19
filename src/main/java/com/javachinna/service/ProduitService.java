package com.javachinna.service;

import com.javachinna.model.Produit;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProduitService {

    List<Produit> getAllProduits ();

   void addProduct (MultipartFile icon ,MultipartFile image , Produit produit)  throws IOException;


    void EditProduct (MultipartFile icon , MultipartFile image , Produit produit) throws IOException;


    void deleteProduct (Long Idproduit);

    Produit getProduitById (Long id );

}
