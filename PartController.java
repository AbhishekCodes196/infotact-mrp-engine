package com.example.demo.controller;

import com.example.demo.model.Part;
import com.example.demo.repository.PartRepository;
import com.example.demo.repository.BomLinkRepository; // Added cleanly at the top
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allows your browser UI to communicate freely
public class PartController {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private BomLinkRepository bomLinkRepository; // Added safely here

     @GetMapping("/parts")
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

     @PostMapping("/parts")
    public Part savePart(@RequestBody Part part) {
        return partRepository.save(part);
    }

     @PutMapping("/parts/{id}")
    public Part updatePart(@PathVariable Long id, @RequestBody Part partDetails) {
        Part part = partRepository.findById(id).orElse(null);
        if (part != null) {
            part.setPartName(partDetails.getPartName());
            part.setCurrentStock(partDetails.getCurrentStock());
            part.setLeadTimeDays(partDetails.getLeadTimeDays());
            return partRepository.save(part);
        }
        return null;
    }
    
     @DeleteMapping("/parts/{id}")
    public void deletePart(@PathVariable Long id) {
        // First, wipe out its relationships so MySQL doesn't throw a foreign key error
        bomLinkRepository.deleteByParentItemId(id);
        bomLinkRepository.deleteByChildItemId(id);
        
         partRepository.deleteById(id);
    }
    
     @PostMapping("/parts/reset")
    public String resetDatabase() {
        partRepository.deleteAll(); // Clears out the part table rows cleanly
        return "{\"status\":\"Database cleared successfully\"}";
    }
}
