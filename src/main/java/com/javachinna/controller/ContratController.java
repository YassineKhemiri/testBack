package com.javachinna.controller;


import com.javachinna.model.Contrat;
import com.javachinna.model.CountType;
import com.javachinna.model.User;
import com.javachinna.service.ContratService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/contrat")
@CrossOrigin("*")
public class ContratController {

    @Autowired
    private ContratService contratService;

    @GetMapping("listeContrats")
    public ResponseEntity<?> getAllContrats()
    {
        return  ResponseEntity.ok(contratService.getAllContarts());
    }

    @GetMapping("PercentageByBranche")
    public ResponseEntity<?> getPercentageByBranche()
    {
        return  ResponseEntity.ok(contratService.getPercentageGroupByBranche());
    }

    @GetMapping("Count")
    public Map<String,Long > countByVisitType() {
        Map<String, Long> result = new HashMap<>();
        List<CountType> counts = contratService.getPercentageGroupByBranche();
        for (CountType row : counts) {
            result.put( row.getBranche().getLibelleBranche(),row.getCount());
        }
        return result;
    }


    @GetMapping("/{id}")
    public Contrat getContratById(@PathVariable(name = "id") Long id) {
        return contratService.getContartById(id);
    }


    @GetMapping("/Mycontrat/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getContratByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(contratService.findContratByUserId(id));
    }


    @DeleteMapping("/{x}")
    public void deleteoffreReservationById(@PathVariable (name = "x") Long x) {
        contratService.deleteContart(x);}
    @PostMapping("/pay/{contratId}")
    public ResponseEntity<?> payForContrat(@PathVariable Long contratId) {
        try {
            String sessionId = contratService.createPaymentSession(contratId);
            return ResponseEntity.ok(sessionId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Payment processing error: " + e.getMessage());
        }
    }
}
