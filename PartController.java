package com.example.demo.controller;

import com.example.demo.model.Part;
import com.example.demo.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PartController {

    @Autowired
    private PartRepository partRepository;

    // 1. Fetch all parts for the dashboard table and dropdowns
    @GetMapping("/parts/all")
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    // 2. Save or Update a part
    @PostMapping("/parts/save")
    public Part savePart(@RequestBody Part part) {
        return partRepository.save(part);
    }

    // 3. Delete a individual part by its ID
    @DeleteMapping("/parts/delete/{id}")
    public void deletePart(@PathVariable Long id) {
        partRepository.deleteById(id);
    }

    // 4. CRITICAL FIX: Endpoint to clear the database table from the UI reset button
    @PostMapping("/parts/reset")
    public void resetDatabase() {
        // Deletes all rows via JPA
        partRepository.deleteAll(); 
    }
}