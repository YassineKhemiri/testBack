package com.javachinna.controller;

import com.javachinna.model.Produit;
import com.javachinna.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin("*")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping("/addProduit")
    public ResponseEntity<?> addProduit(@RequestParam("fileIcon") MultipartFile fileIcon,@RequestParam("file") MultipartFile file, @Valid Produit produit) {
        try {
            produitService.addProduct(fileIcon,file,produit);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "icon and image uploaded and form data saved successfully");
            return ResponseEntity.ok(response);  // Send JSON response
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Send JSON response
        }
    }


    @PutMapping("/editProduit")
    public ResponseEntity<?> EditProduit(@RequestParam("fileIcon") MultipartFile fileIcon,@RequestParam("file") MultipartFile file, @Valid Produit produit) {
        try {
            produitService.EditProduct(fileIcon,file,produit);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "icon and image uploaded and form data saved successfully");
            return ResponseEntity.ok(response);  // Send JSON response
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Send JSON response
        }
    }

    @GetMapping("/getproduit/{id}")
    public ResponseEntity<?> getBrancheById(@PathVariable(name = "id") Long id)
    {
        return  ResponseEntity.ok(produitService.getProduitById(id));
    }


    @GetMapping
    @RequestMapping("/getallProduits")
    public ResponseEntity<List<Produit>> getAllProduits() {
        List<Produit> produits = produitService.getAllProduits();
        if (produits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produits);
    }

    @DeleteMapping("/deleteProduit/{x}")
    public void deleteBrancheById(@PathVariable (name = "x") Long x) {

        produitService.deleteProduct(x);
    }


}
