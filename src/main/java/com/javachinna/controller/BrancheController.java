package com.javachinna.controller;

import com.javachinna.model.Branche;
import com.javachinna.service.BrancheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BrancheController {

    @Autowired
    private BrancheService brancheService;


    @GetMapping
    public List<Branche> getAllBranches(){
        return brancheService.getAllBranches();
    }

    @GetMapping("/{id}")
    public Branche getBranche(@PathVariable (name = "id") Long id) {
        return brancheService.getBrancheById(id);
    }

    @GetMapping("/branche/{libelle}")
    public List<Branche> getBrancheByLibelle(@PathVariable (name = "libelle") String libelle) {
        return brancheService.findBrancheByLibelleBranche(libelle);
    }

    @GetMapping("/branche/code/{code}")
    public Branche getBrancheByCode(@PathVariable (name = "code") String code) {
        return brancheService.findBrancheByCodeBranche(code);
    }


    @DeleteMapping("/{x}")
    public void deleteBrancheById(@PathVariable (name = "x") Long x) {
        brancheService.deleteBranche(x);
    }



    @PutMapping
    public Branche EditBranche(@RequestBody Branche b) {
        return brancheService.updateBranche(b);
    }

    @PostMapping
    public Branche addBranche(@RequestBody Branche b) {
        return brancheService.addBranche(b);
    }

}
