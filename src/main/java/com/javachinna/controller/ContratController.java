package com.javachinna.controller;


import com.javachinna.model.Contrat;
import com.javachinna.model.User;
import com.javachinna.service.ContratService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ContratController {

    @Autowired
    private ContratService contratService;

    @GetMapping
    public List<Contrat> getAllContrats(){
        return contratService.getAllContarts();
    }

    @GetMapping("/{id}")
    public Contrat getContratById(@PathVariable(name = "id") Long id) {
        return contratService.getContartById(id);
    }


    @GetMapping("/contrat/{id}")
    public ResponseEntity<?> getContratByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(contratService.findContratByUserId(id));
    }


    @DeleteMapping("/{x}")
    public void deleteoffreReservationById(@PathVariable (name = "x") Long x) {
        contratService.deleteContart(x);}
}
